package com.delivery.cart.adapter.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateEvent {

	private UUID orderId;
    private OrderStatus status;
    private UUID restaurantId;
}
