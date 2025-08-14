package com.order.track.core.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.model.OrderStatus;
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
	
	private Boolean rating_done;
	
	private LocalDateTime order_place_date;
	
	private LocalDateTime order_dispatched_date;
	
	private LocalDateTime delivery_person_assign_date;
	
	private LocalDateTime order_delivery_date;
	
	private LocalDateTime order_cancel_date;
	
	private LocalDateTime out_for_delivery_date;
	
	@CreationTimestamp
	private LocalDateTime create_at;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<OrderItem> items;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Payment> payments;
	
}
