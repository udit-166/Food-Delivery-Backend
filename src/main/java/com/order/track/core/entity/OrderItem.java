package com.order.track.core.entity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.order.track.adapter.constant.AppConstant;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = AppConstant.ORDER_ITEM_ENTITY)
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
	@JsonBackReference
	private Order order;
	
	private UUID foodItemId;
	
	private String food_name;
	
	private Integer quantity;
	
	private BigDecimal price;
	
	private UUID delivery_id;
	
	@CreationTimestamp
	private LocalTime created_at;
	
	@UpdateTimestamp
	private LocalTime updated_at;
}
