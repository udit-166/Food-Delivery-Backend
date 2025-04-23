package com.order.track.adapter.respository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.order.track.adapter.model.PaymentStatus;
import com.order.track.core.entity.Payment;
import com.order.track.core.repositories.PaymentRepositories;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{
	
	@Autowired
	private PaymentRepositories paymentRepositories;

	@Override
	public Payment save(Payment payment) {
		return paymentRepositories.save(payment);
	}

	@Override
	public Payment getPaymentById(UUID payment_id) {
		Optional<Payment> payment = paymentRepositories.findById(payment_id);
		
		if(payment.isPresent()) {
			Payment result  = payment.get();
			return result;
		}
		return null;
	}

	@Override
	public List<Payment> findByOrder_Id(UUID orderId) {
		return paymentRepositories.findByOrder_Id(orderId);
	}

	@Override
	public Payment findByOrder_IdAndStatus(UUID orderId, PaymentStatus status) {
		return paymentRepositories.findByOrder_IdAndStatus(orderId, status);
	}

	@Override
	public List<Payment> findByStatus(PaymentStatus status) {
		
		return paymentRepositories.findByStatus(status);
	}

	@Override
	public BigDecimal getTotalEarningsForRestaurant(UUID restaurantId) {
		
		return paymentRepositories.getTotalEarningsForRestaurant(restaurantId);
	}

	@Override
	public List<Payment> saveAll(List<Payment> payment) {
		return paymentRepositories.saveAll(payment);
	}

	

}
