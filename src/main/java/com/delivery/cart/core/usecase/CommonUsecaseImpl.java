package com.delivery.cart.core.usecase;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.adapter.models.AssignDeliverPartnerRequest;
import com.delivery.cart.adapter.models.PartnerEta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommonUsecaseImpl implements CommonUsecase{
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;


	@Override
	public List<String> fetchGeoDeliveryPersons(double lat, double lng, double radiusKm, int limit) {
	    GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();

	    // IMPORTANT: Point(x, y) -> (longitude, latitude)
	    Circle circle = new Circle(new Point(lng, lat), new Distance(radiusKm, Metrics.KILOMETERS));
	    GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(AppConstant.GEO_KEY,
	            circle, RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
	                    .includeDistance()
	                    .sortAscending()
	                    .limit(limit));

	    if (results == null) return Collections.emptyList();

	    return results.getContent().stream()
	            .map(r -> r.getContent().getName())
	            .collect(Collectors.toList());
	}

	@Override
	public List<PartnerEta> fetcheEtaFromGoogle(AssignDeliverPartnerRequest req, List<String> partnerIds) {
	    if (partnerIds.isEmpty()) { 
	    	return Collections.emptyList();
	    }
	    DecimalFormat df = new DecimalFormat("#.######");

	    String origin = df.format(req.getPickupLocation().getLatitude()) + "," + df.format(req.getPickupLocation().getLongitude());

	    List<String> validPartnerIds = new ArrayList<>();
	    List<String> destinationPoints = new ArrayList<>();

	    for (String pid : partnerIds) {
	        List<Point> pos = redisTemplate.opsForGeo().position(AppConstant.GEO_KEY, pid);
	        if (pos != null && !pos.isEmpty() && pos.get(0) != null) {
	            Point p = pos.get(0);
	            
	            destinationPoints.add(df.format(p.getY()) + "," + df.format(p.getX())); // lat,lng
	            validPartnerIds.add(pid);
	        }
	    }
	    
	    System.out.println("Fetching ETA for origin: " + origin);
	    System.out.println("Partner destinations: " + destinationPoints);

	    if (destinationPoints.isEmpty()) {return Collections.emptyList();}

	    String destinations = String.join("|", destinationPoints);
	    String url = "https://maps.googleapis.com/maps/api/distancematrix/json" +
	            "?origins=" + URLEncoder.encode(origin, StandardCharsets.UTF_8) +
	            "&destinations=" + URLEncoder.encode(destinations, StandardCharsets.UTF_8) +
	            "&mode=driving&departure_time=now" +
	            "&key=" + AppConstant.GOOGLE_MAP_API_KEY;

	    try {
	        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
	        
	        System.out.println("Calling Google API: " + url);
	        System.out.println("Google raw response: " + resp.getBody());
	        JsonNode root = objectMapper.readTree(resp.getBody());

	        JsonNode rows = root.path("rows");
	        if (!rows.isArray() || rows.size() == 0) return Collections.emptyList();

	        JsonNode elements = rows.get(0).path("elements");
	        List<PartnerEta> result = new ArrayList<>();

	        for (int i = 0; i < elements.size() && i < validPartnerIds.size(); i++) {
	            JsonNode e = elements.get(i);
	            long eta = e.path("duration_in_traffic").path("value")
	                    .asLong(e.path("duration").path("value").asLong(0));
	            result.add(new PartnerEta(validPartnerIds.get(i), eta));
	        }
	        return result;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return Collections.emptyList();
	    }
	}


	@Override
	public boolean tryAtomicAssignDeliveryPartner(UUID orderId, String partnerId) {
		String key = String.format(AppConstant.PARTNER_LOCK_KEY, partnerId);
		
		Boolean ok = redisTemplate.opsForValue().setIfAbsent(key, orderId.toString(), Duration.ofMinutes(10));
		
		return Boolean.TRUE.equals(ok);
	}

	@Override
	public void releaseLock(String orderId) {
		String key = String.format(AppConstant.ORDER_LOCK_PREFIX, orderId);
		
		redisTemplate.delete(key);
	}

	@Override
	public double computeRadiusForAttempt(int attempt) {
		double base = 3.0;
		double radius = base * Math.pow(1.5, attempt);
		
		return Math.min(radius, 20.0);
	}

	@Override
	public boolean isDeliveryPartnerAvailable(String partnerId) {
		String key = String.format(AppConstant.PARTNER_STATUS_PREFIX, partnerId);
		System.out.println("key is " + key);
		String status = redisTemplate.opsForValue().get(key);
		System.out.println("The status is " + status);
		return "AVAILABLE".equalsIgnoreCase(status);
	}

	@Override
	public String fetchDeliveryPartnerName(String partnerId) {
		String key = String.format(AppConstant.PARTNER_NAME_KEY, partnerId);
		
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void markPartnerBusy(String partnerId, UUID orderId) {
		String statusKey = String.format(AppConstant.PARTNER_STATUS_PREFIX, partnerId);
		
		redisTemplate.opsForValue().set(statusKey, "BUSY");
		
		String lockKey = String.format(AppConstant.PARTNER_LOCK_KEY, partnerId);
		
		redisTemplate.opsForValue().set(lockKey, orderId.toString(), Duration.ofMinutes(10));
		
	}

	@Override
	public boolean acquireLocked(String orderId, int seconds) {
		String key = String.format(AppConstant.ORDER_LOCK_PREFIX, orderId);
		 Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "LOCKED", Duration.ofSeconds(seconds));
		 
		 return Boolean.TRUE.equals(success);
	}

	@Override
	public int getAttemptsCount(UUID orderId) {
		 String key = String.format(AppConstant.ORDER_ATTEMPT_PREFIX, orderId.toString());
		    String v = redisTemplate.opsForValue().get(key);
		    if (v == null) return 0;
		    try {
		        return Integer.parseInt(v);
		    } catch (NumberFormatException ex) {
		        return 0;
		 }
	}

	@Override
	public int incrementAttemptsCount(UUID orderId) {
		String key = String.format(AppConstant.ORDER_ATTEMPT_PREFIX, orderId.toString());
	    Long val = redisTemplate.opsForValue().increment(key);
	    if (val != null && val == 1) {
	        redisTemplate.expire(key, Duration.ofHours(6));
	    }
	    return val == null ? 0 : val.intValue();
	}

	@Override
	public void resetAttemptsCount(UUID orderId) {
		 String key = String.format(AppConstant.ORDER_ATTEMPT_PREFIX, orderId.toString());
		 redisTemplate.delete(key);
		
	}
	
	@Override
	public void scheduleRetryToSearchDeliveryPerson(AssignDeliverPartnerRequest request) {
		 int attempts = incrementAttemptsCount(request.getOrder_id());
		    if (attempts >= AppConstant.MAX_ATTEMPTS) {
		        // Move to a forced-assign queue / zset so scheduler can force assign
		        long now = System.currentTimeMillis();
		        redisTemplate.opsForZSet().add(AppConstant.RETRY_ORDER_FORCED_KEY, request.getOrder_id().toString(), now);
		        return;
		    }
		    long retryTime = System.currentTimeMillis() + (2L * 60L * 1000L); // 2 minutes
		    redisTemplate.opsForZSet().add(AppConstant.RETRY_ORDER_KEY, request.getOrder_id().toString(), retryTime);
	}
	
	@Override
	public void registerPartnerInGeo(String partnerId, double lat, double lng) {
	    String redisKey = AppConstant.GEO_KEY;
	    redisTemplate.opsForGeo().add(
	        redisKey,
	        new Point(lng, lat), // Redis expects longitude first
	        partnerId
	    );

	    // Optional: Set TTL so stale entries are auto-removed if partner never updates
	    redisTemplate.expire(redisKey, Duration.ofHours(12));
	}
	
	@Override
	public void setPartnerOnline(String partnerId, double lat, double lng, String name) {
	    registerPartnerInGeo(partnerId, lat, lng);
	    String key1 = String.format(AppConstant.PARTNER_STATUS_PREFIX, partnerId);
	    System.out.println("The key is"+ key1);
	    redisTemplate.opsForValue().set(
	    		key1,
	        "AVAILABLE",
	        Duration.ofHours(12) // Auto-remove after some time
	    );
	    
	    String key2 = String.format(AppConstant.PARTNER_NAME_KEY, partnerId);
	    redisTemplate.opsForValue().set(
	            key2,
	            name, // yaha actual naam dalna h
	            Duration.ofHours(12)
	     );
	}

}
