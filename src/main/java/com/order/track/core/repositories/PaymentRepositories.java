package com.order.track.core.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.order.track.adapter.model.PaymentStatus;
import com.order.track.core.entity.Payment;

@Repository
public interface PaymentRepositories extends JpaRepository<Payment, UUID>{
	
	List<Payment> findByOrder_Id(UUID orderId);
	
	Payment findByOrder_IdAndStatus(UUID orderId, PaymentStatus status);
	
	List<Payment> findByStatus(PaymentStatus status);
	
	@Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order.restaurantId = :restaurantId AND p.status = com.order.track.adapter.model.PaymentStatus.SUCCESS")
    BigDecimal getTotalEarningsForRestaurant(UUID restaurantId);

}
