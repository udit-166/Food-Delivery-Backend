package com.order.track.adapter.respository;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.order.track.core.entity.OrderHistory;
import com.order.track.core.repositories.OrderHistoryRepo;

@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository{
	
	@Autowired
	private OrderHistoryRepo orderHistoryRepo;

	@Override
	public OrderHistory save(OrderHistory order) {
		
		return orderHistoryRepo.save(order);
	}

	@Override
	public OrderHistory findByOrderId(UUID order_id) {
		
		return orderHistoryRepo.findByOrderId(order_id);
	}

}
