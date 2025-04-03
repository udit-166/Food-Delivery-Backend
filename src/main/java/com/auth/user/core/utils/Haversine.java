package com.auth.user.core.utils;

public class Haversine {

	public double haversine(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371;
		
		double dlat = Math.toRadians(lat2-lat1);
		double dlon = Math.toRadians(lon2-lon1);
		
		double a = Math.sin(dlat/2) * Math.sin(dlat/2)+
				Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))*
				Math.sin(dlon/2)* Math.sin(dlon/2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		return R*c;
	}
}
