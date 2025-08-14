package com.delivery.cart.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.delivery.cart.adapter.constant.AppConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name=AppConstant.REVIEW_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private UUID deliveryId;
	
	private String reviews;
	
	private int rating;
	
	@CreationTimestamp
	private LocalDateTime create_at;
	
	@UpdateTimestamp
	private LocalDateTime updated_at;

}
