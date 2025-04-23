package com.order.track.adapter.respository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.order.track.adapter.model.PaymentStatus;
import com.order.track.core.entity.Payment;

public interface PaymentRepository {

	Payment save(Payment payment);
	
	List<Payment> saveAll(List<Payment> payment);
	
	Payment getPaymentById(UUID payment_id);
	
	List<Payment> findByOrder_Id(UUID orderId);
	
	Payment findByOrder_IdAndStatus(UUID orderId, PaymentStatus status);
	
	List<Payment> findByStatus(PaymentStatus status);
	
	BigDecimal getTotalEarningsForRestaurant(UUID restaurantId);
}
