package com.delivery.cart.adapter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerEta {
	
	private String partnerId;
	
	private long etaSeconds;

}
