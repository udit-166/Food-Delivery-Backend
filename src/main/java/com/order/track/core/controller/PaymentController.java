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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.model.GenericResponse;
import com.order.track.adapter.model.InitiatePaymentRequestDto;
import com.order.track.adapter.model.PaymentDTO;
import com.order.track.adapter.model.PaymentStatus;
import com.order.track.adapter.model.StatusCode;
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
	GenericResponse<PaymentDTO> initiatePayment(@RequestBody InitiatePaymentRequestDto request) throws RazorpayException{
		try {
		return new GenericResponse<PaymentDTO>("Payment Initiated successfully!!",StatusCode.of(HttpStatus.OK),  paymentService.initiatPayment(request.getOrder_id()));
		}catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.VERIFY_PAYMENT)
	GenericResponse<PaymentDTO> verifyPayment(@RequestBody VerifyPaymentRequest request){
		try {
			return new GenericResponse<>("Payment verified successfully!!",StatusCode.of(HttpStatus.OK), paymentService.verifyPayment(request));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.REFUND_PAYMENT)
	GenericResponse<PaymentDTO> refundPayment(@RequestParam UUID payment_id) throws RazorpayException{
		try {
		return new GenericResponse<PaymentDTO>("Payment refund process initiated succcessfully!!",StatusCode.of(HttpStatus.OK), paymentService.refundPayment(payment_id));
		}catch(Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.PAYMENTS_BY_ORDER_ID)
	GenericResponse<List<Payment>> getListOfPaymentByOrderId(@PathVariable UUID orderId){
		try {
			return new GenericResponse<List<Payment>>("Fetched list of payment successfully!!",StatusCode.of(HttpStatus.OK), paymentService.getListOfPaymentOfOrder(orderId));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.SUCCESS_PAYMENT_BY_ORDER_ID)
	GenericResponse<PaymentDTO> getSuccessPayment(@PathVariable UUID orderId){
		try {
			return new GenericResponse<PaymentDTO>("Fetched list of successfull payment!!",StatusCode.of(HttpStatus.OK), paymentService.getSuccessPaymentList(orderId));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.PAYMENTS_BY_STATUS)
	GenericResponse<List<Payment>> getPaymentByStatus(@PathVariable PaymentStatus status){
		try {
			return new GenericResponse<>("The payment with status has been fetched successfully!!",StatusCode.of(HttpStatus.OK), paymentService.getPaymentByStatus(status));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_TOTAL_EARNING_OF_RESTAURANT)
	GenericResponse<BigDecimal> getTotalEarningsForRestaurant(@PathVariable UUID restaurant_id){
		try {
			return new GenericResponse<>("fetched total earning of restaurant successfully!!",StatusCode.of(HttpStatus.OK), paymentService.getTotalEarningsForRestaurant(restaurant_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PostMapping(AppConstant.RETRY_PAYMENT)
	GenericResponse<PaymentDTO> retryPayment(@RequestParam UUID order_id){
		try {
			return new GenericResponse<>("Request initiated to retry the payment",StatusCode.of(HttpStatus.OK), paymentService.retryPayment(order_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
}
