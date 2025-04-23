package com.order.track.adapter.respository;

import java.util.UUID;

import com.order.track.core.entity.OrderHistory;

public interface OrderHistoryRepository {

	OrderHistory save(OrderHistory order);
	
	OrderHistory findByOrderId(UUID order_id);
}
