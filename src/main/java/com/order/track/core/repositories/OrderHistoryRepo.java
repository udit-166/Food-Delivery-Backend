package com.order.track.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.track.core.entity.OrderHistory;

public interface OrderHistoryRepo extends JpaRepository<OrderHistory , UUID>{

	OrderHistory findByOrderId(UUID orderId);
}
