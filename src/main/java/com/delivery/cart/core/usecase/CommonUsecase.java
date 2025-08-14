package com.delivery.cart.core.usecase;

import java.util.List;
import java.util.UUID;

import com.delivery.cart.adapter.models.AssignDeliverPartnerRequest;
import com.delivery.cart.adapter.models.PartnerEta;

public interface CommonUsecase {

	public List<String> fetchGeoDeliveryPersons(double lat, double lng, double radiusKm, int limit);
	
	public List<PartnerEta> fetcheEtaFromGoogle(AssignDeliverPartnerRequest req, List<String> partnerIds);
	
	public boolean tryAtomicAssignDeliveryPartner(UUID orderId, String partnerId);
	
	public boolean acquireLocked(String orderId, int seconds);
	
	public void releaseLock(String orderId);
	
	public double computeRadiusForAttempt(int attempt);
	
	public boolean isDeliveryPartnerAvailable(String partnerId);
	
	public String fetchDeliveryPartnerName(String partnerId);
	
	public void markPartnerBusy(String partnerId, UUID orderId);
	
	public void scheduleRetryToSearchDeliveryPerson(AssignDeliverPartnerRequest request);
	
	public int getAttemptsCount(UUID orderId);
	
	public int incrementAttemptsCount(UUID orderId);
	
	public void resetAttemptsCount(UUID orderId);
	
	public void registerPartnerInGeo(String partnerId, double lat, double lng);
	
	public void setPartnerOnline(String partnerId, double lat, double lng, String name);
}
