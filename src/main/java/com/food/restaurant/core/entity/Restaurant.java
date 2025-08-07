package com.food.restaurant.core.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.food.restaurant.adapter.constant.AppConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = AppConstant.RESTAURANT_ENTITY)
public class Restaurant {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String image_url;
	
	@Column(nullable = true)
	private  Double average_rating;
	
	@Column(nullable = true)
	private Integer total_rating;
	
	@Column(nullable = true)
	private String restaurant_email;
	
	@Column(nullable = true)
	private String customer_care_number;
	
	@Column(nullable = true)
	private String opening_time;
	
	@Column(nullable = true)
	private String closing_time;
	
	private UUID user_id;
	
	private Boolean is_active;
	
	@Column(nullable = true)
	private ArrayList<UUID> categoriesId;
	
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@Column(nullable = true)
	@JsonManagedReference
    private List<FoodItem> foodItems;
	
	@CreationTimestamp
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	private LocalDateTime updated_at;
	
}
