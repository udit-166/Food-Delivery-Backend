package com.delivery.cart.adapter.models;

import java.util.UUID;

import com.delivery.cart.core.models.Location;

import lombok.Data;

@Data
public class RegisterVehicleDetailsRequest {

	private UUID delivery_partner_id;
	
	private String driving_license_name;
	
	private String vehicle_number;
	
	private String vehicle_type;
	
	private String driving_license_doc;
	
	private String rc_book_doc;
	
	private Double lat;
	
	private Double lng;
	
	private String name;
	
}
