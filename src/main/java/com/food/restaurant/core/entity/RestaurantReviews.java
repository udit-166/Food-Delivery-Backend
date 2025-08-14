package com.food.restaurant.core.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.food.restaurant.adapter.constant.AppConstant;

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
@Table(name= AppConstant.RESTAURANT_REVIEWS_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantReviews {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private UUID restaurant_id;
	
	private int rating;
	
	private String reviews;
	
	private UUID order_id;
	
	private ArrayList<String> imageUrl;
	
	@CreationTimestamp
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	private LocalDateTime updated_at;
	
}
