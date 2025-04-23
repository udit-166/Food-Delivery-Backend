package com.order.track.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.order.track.core.entity.OrderItem;

public interface OrderItemRepositories extends JpaRepository<OrderItem, UUID>{

}
