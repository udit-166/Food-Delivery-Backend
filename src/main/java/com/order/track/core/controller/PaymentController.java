package com.order.track.core.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.model.PaymentDTO;
import com.order.track.adapter.model.PaymentStatus;
import com.order.track.adapter.model.VerifyPaymentRequest;
import com.order.track.adapter.service.PaymentService;
import com.order.track.core.entity.Payment;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping(AppConstant.PAYMENT_CONTOLLER)
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping(AppConstant.INITIATE_PAYMENT)
	ResponseEntity<PaymentDTO> initiatePayment(@RequestParam UUID orderId) throws RazorpayException{
		return new ResponseEntity<PaymentDTO>(paymentService.initiatPayment(orderId), HttpStatus.OK);
	}
	
	@PutMapping(AppConstant.VERIFY_PAYMENT)
	ResponseEntity<PaymentDTO> verifyPayment(@RequestParam VerifyPaymentRequest request){
		try {
			return new ResponseEntity<>(paymentService.verifyPayment(request), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstant.REFUND_PAYMENT)
	ResponseEntity<PaymentDTO> refundPayment(@RequestParam UUID paymentId) throws RazorpayException{
		return new ResponseEntity<PaymentDTO>(paymentService.refundPayment(paymentId), HttpStatus.OK);
	}
	
	@GetMapping(AppConstant.PAYMENTS_BY_ORDER_ID)
	ResponseEntity<List<Payment>> getListOfPaymentByOrderId(@PathVariable UUID orderId){
		try {
			return new ResponseEntity<List<Payment>>(paymentService.getListOfPaymentOfOrder(orderId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.SUCCESS_PAYMENT_BY_ORDER_ID)
	ResponseEntity<PaymentDTO> getSuccessPayment(@PathVariable UUID orderId){
		try {
			return new ResponseEntity<PaymentDTO>(paymentService.getSuccessPaymentList(orderId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.PAYMENTS_BY_STATUS)
	ResponseEntity<List<Payment>> getPaymentByStatus(@PathVariable PaymentStatus status){
		try {
			return new ResponseEntity<>(paymentService.getPaymentByStatus(status), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.GET_TOTAL_EARNING_OF_RESTAURANT)
	ResponseEntity<BigDecimal> getTotalEarningsForRestaurant(@PathVariable UUID restaurant_id){
		try {
			return new ResponseEntity<>(paymentService.getTotalEarningsForRestaurant(restaurant_id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(AppConstant.RETRY_PAYMENT)
	ResponseEntity<PaymentDTO> retryPayment(@RequestParam UUID order_id){
		try {
			return new ResponseEntity<>(paymentService.retryPayment(order_id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
