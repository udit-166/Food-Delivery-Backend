package com.auth.user.core.model;

import java.util.ArrayList;
import java.util.List;

import com.auth.user.core.entity.Address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressResponse {
	
	private ArrayList<Address> addresses;
	private String message;
}
