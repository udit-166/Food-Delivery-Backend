package com.delivery.cart.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.adapter.models.DeliveryStatus;
import com.delivery.cart.core.models.Location;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = AppConstant.DELIVERY_TABLE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deliveries {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	private UUID restuarant_id;
	
	private UUID customer_id;
	
	private UUID delivery_partner_id;
	
	private String delivery_partner_name;
	
	private UUID order_id;
	
	private DeliveryStatus status;
	
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name = "address", column = @Column(name = "pickup_address")),
	    @AttributeOverride(name = "latitude", column = @Column(name = "pickup_latitude")),
	    @AttributeOverride(name = "longitude", column = @Column(name = "pickup_longitude"))
	})
	private Location pickUpLocation;
	
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name = "address", column = @Column(name = "drop_address")),
	    @AttributeOverride(name = "latitude", column = @Column(name = "drop_latitude")),
	    @AttributeOverride(name = "longitude", column = @Column(name = "drop_longitude"))
	})
	private Location dropLocation;
	
	private LocalDateTime assignDateTime;
	
	private LocalDateTime pickUpDateTime;
	
	
	private LocalDateTime deliveryDateTime;
	
	@CreationTimestamp
	private LocalDateTime create_at;
	
	@UpdateTimestamp
	private LocalDateTime updated_at;

}
