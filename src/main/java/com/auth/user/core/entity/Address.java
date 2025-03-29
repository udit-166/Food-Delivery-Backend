package com.auth.user.core.entity;

import java.util.UUID;

import com.auth.user.common.constant.AppConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = AppConstants.FDAADDRESS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String address;
	
	private Double latitude;
	
	private Double longitude;
	
	private boolean isDefault;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
