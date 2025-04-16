package com.food.restaurant.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.food.restaurant.adapter.constant.AppConstant;

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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = AppConstant.FOOD_ITEM_ENTITY)
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private String name;
	
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "category_id")
	private Categories category;
	
	private Integer price;
	
	private Double rating;
	
	private String imageUrl;
	
	private Boolean is_active;
	
	@ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
	
	@CreationTimestamp
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	private LocalDateTime updated_at;
}
