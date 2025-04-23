package com.order.track.core.usecase;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.order.track.adapter.model.PaymentDTO;
import com.order.track.adapter.model.PaymentStatus;
import com.order.track.adapter.model.VerifyPaymentRequest;
import com.order.track.core.entity.Payment;
import com.razorpay.RazorpayException;

public interface PaymentUsecase {

	public PaymentDTO initiatePayment(UUID orderId) throws RazorpayException;
	
	public PaymentDTO verifyPayment(VerifyPaymentRequest  request);
	
	public PaymentDTO refundPayment(UUID payment_id) throws RazorpayException;
	
	public List<Payment> getListOfPaymentOfOrder(UUID order_id);
	
	public PaymentDTO getSuccessPaymentList(UUID order_id);
	
	public List<Payment> getPaymentByStatus(PaymentStatus status);
	
	public BigDecimal getTotalEarningsForRestaurant(UUID restaurantId);
	
	public PaymentDTO retryPayment(UUID order_id) throws RazorpayException;
}
