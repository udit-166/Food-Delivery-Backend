package com.order.track.core.entity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.model.OrderStatus;

import jakarta.persistence.CascadeType;
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


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name=AppConstant.ORDER_ENTITY)
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private UUID customerId;     //from auth-service
	
	private UUID restaurantId;    // from  restaurant-service
	
	private OrderStatus status;
	
	private BigDecimal totalPrice;
	
	private Boolean isActive;
	
	@CreationTimestamp
	private LocalTime create_at;
	
	@UpdateTimestamp
	private LocalTime updated_at;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items;
}
