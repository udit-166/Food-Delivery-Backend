package com.order.track.adapter.model;

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
