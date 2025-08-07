package com.order.track.adapter.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.order.track.adapter.model.PaymentDTO;
import com.order.track.adapter.model.PaymentStatus;
import com.order.track.adapter.model.VerifyPaymentRequest;
import com.order.track.adapter.service.PaymentService;
import com.order.track.core.entity.Payment;
import com.order.track.core.usecase.PaymentUsecase;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentUsecase paymentUsecase;

	@Override
	public PaymentDTO initiatPayment(UUID orderId) throws RazorpayException {
		return paymentUsecase.initiatePayment(orderId);
	}

	@Override
	public PaymentDTO verifyPayment(VerifyPaymentRequest request) {
		return paymentUsecase.verifyPayment(request);
	}

	@Override
	public PaymentDTO refundPayment(UUID payment_id) throws RazorpayException {
		return paymentUsecase.refundPayment(payment_id);
	}

	@Override
	public List<Payment> getListOfPaymentOfOrder(UUID order_id) {
		
		return paymentUsecase.getListOfPaymentOfOrder(order_id);
	}

	@Override
	public PaymentDTO getSuccessPaymentList(UUID order_id) {
		
		return paymentUsecase.getSuccessPaymentList(order_id);
	}

	@Override
	public List<Payment> getPaymentByStatus(PaymentStatus status) {
		
		return paymentUsecase.getPaymentByStatus(status);
	}

	@Override
	public BigDecimal getTotalEarningsForRestaurant(UUID restaurantId) {
		
		return paymentUsecase.getTotalEarningsForRestaurant(restaurantId);
	}

	@Override
	public PaymentDTO retryPayment(UUID orderId) throws RazorpayException {
		
		return paymentUsecase.retryPayment(orderId);
	}

}
