package com.order.track.adapter.components;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.model.PaymentStatus;
import com.order.track.adapter.respository.OrderRepository;
import com.order.track.adapter.respository.PaymentRepository;
import com.order.track.core.entity.Order;
import com.order.track.core.entity.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class AutoCancelOrderScheduler {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Scheduled(fixedRate = 5 * 60 * 1000)
	@Transactional
	public void autoCancelUnpaidOrders() {
		List<Order> pendingOrder = orderRepository.findByStatus(OrderStatus.PENDING);
		
		for(Order order : pendingOrder) {
			List<Payment> payments = paymentRepository.findByOrder_Id(order.getId());
			
			payments.stream().filter(p -> p.getStatus() == PaymentStatus.PENDING)
			.filter(p -> p.getCreate_at().plusMinutes(AppConstant.PAYMENT_TIMEOUT_MINUTES).isBefore(LocalTime.now()))
			.findFirst()
			.ifPresent(p -> {
				order.setStatus(OrderStatus.CANCELLED);
				orderRepository.save(order);
				p.setStatus(PaymentStatus.EXPIRED);
				paymentRepository.save(p);
			});
		}
	}
}
