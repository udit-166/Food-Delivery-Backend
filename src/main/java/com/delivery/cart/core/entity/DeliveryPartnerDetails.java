package com.delivery.cart.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.core.models.VerificationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= AppConstant.VEHICLE_DETAILS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPartnerDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private UUID delivery_partner_id;
	
	private String driving_license_name;
	
	private String vehicle_number;
	
	private String vehicle_type;
	
	private String driving_license_doc;
	
	private String rc_book_doc;
	
	private Boolean is_validation;
	
	private VerificationStatus status;
	
	
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
