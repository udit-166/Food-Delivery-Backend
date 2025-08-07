package com.order.track.core.entity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.model.PaymentStatus;
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
@Table(name = AppConstant.PAYMENT)
public class Payment {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
    @JoinColumn(name = "order_id")
	@JsonBackReference
    private Order order;
	
	private String razorpayOrderId;
	
	private String razorpayPaymentId;
	
	private String razorpaySignature;
	
	private BigDecimal amount;
	 
	private String currency;
	
	private PaymentStatus status;
	
	@CreationTimestamp
	private LocalTime create_at;
	
	@UpdateTimestamp
	private LocalTime updated_at;
	
}
