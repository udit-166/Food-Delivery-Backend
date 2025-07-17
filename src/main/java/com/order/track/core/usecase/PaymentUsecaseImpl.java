package com.order.track.core.usecase;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.order.track.adapter.constant.AppConstant;
import com.order.track.adapter.mapper.PaymentMapper;
import com.order.track.adapter.model.FcmNotification;
import com.order.track.adapter.model.HandleNotificationRequest;
import com.order.track.adapter.model.OrderDTO;
import com.order.track.adapter.model.PaymentDTO;
import com.order.track.adapter.model.PaymentStatus;
import com.order.track.adapter.model.VerifyPaymentRequest;
import com.order.track.adapter.respository.OrderHistoryRepository;
import com.order.track.adapter.respository.PaymentRepository;
import com.order.track.adapter.service.OrderService;
import com.order.track.core.entity.OrderHistory;
import com.order.track.core.entity.Payment;
import com.order.track.core.utils.VerifySignature;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentUsecaseImpl implements PaymentUsecase{
	
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderUsecase orderUsecase;
	
	@Autowired
	private OrderHistoryRepository orderHistoryRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	

	private VerifySignature verifySignature;

	private PaymentMapper paymentMapper;
	
	@Autowired
	private KafkaTemplate<String, HandleNotificationRequest> kafkaTemplate;


	@Override
	public PaymentDTO initiatePayment(UUID orderId) throws RazorpayException {
			OrderDTO order = orderService.getOrderById(orderId);
		
		
			RazorpayClient razorpay = new RazorpayClient(AppConstant.RAZORPAY_KEY, AppConstant.RAZORPAY_SECRET);
			
			JSONObject options = new JSONObject();
			
			options.put("amount", order.getTotalPrice().multiply(new BigDecimal(100)));
			options.put("currency", "INR");
			options.put("reciept",orderId.toString());
			options.put("payment_capture", 1);
			
			Order razorpayOrder = razorpay.orders.create(options);
			
			Payment payment = new Payment();
			
			com.order.track.core.entity.Order order1 = orderUsecase.getOrderById(orderId);
			
			payment.setAmount(order.getTotalPrice());
			payment.setCurrency(razorpayOrder.get("currency"));
			payment.setOrder(order1);
			payment.setRazorpayOrderId(razorpayOrder.get("id"));
			payment.setStatus(PaymentStatus.PENDING);
			
			paymentRepository.save(payment);
			
			PaymentDTO paymentDTO = paymentMapper.entityToDto(payment);
			
			return paymentDTO;

	}


	@Override
	public PaymentDTO verifyPayment(VerifyPaymentRequest request) {
		String orderId = request.getRazorpayOrderId();
		
		String paymentId = request.getRazorpayPaymentId();
		
		String verifySignatures = request.getRazorpaySignature();
		
		boolean isSignatureVerified = verifySignature.verifySignature(orderId, paymentId, verifySignatures);
		Payment payment = paymentRepository.getPaymentById(request.getPaymentId());
		if(isSignatureVerified) {
			payment.setRazorpayOrderId(orderId);
			payment.setRazorpayPaymentId(paymentId);
			payment.setRazorpaySignature(verifySignatures);
			payment.setStatus(PaymentStatus.SUCCESS);
			
			Payment result = paymentRepository.save(payment);
			
			PaymentDTO result1 = paymentMapper.entityToDto(result);
			
			OrderHistory history = new OrderHistory();
			
			history.setOrderId(null);
			
			orderHistoryRepository.save(history);

			HandleNotificationRequest succes_request = new HandleNotificationRequest();
			
			succes_request.setEmail("udhishahi1606@gmail.com");   //we have to make it dynamics
			succes_request.setFcmToken("");
			succes_request.setPhone("8887943623");
			
			kafkaTemplate.send("handle_payment_success", succes_request);
			return result1;
		}
		else {
			payment.setStatus(PaymentStatus.FAILED);
			Payment result = paymentRepository.save(payment);
			PaymentDTO result1 = paymentMapper.entityToDto(result);
			HandleNotificationRequest failed_request = new HandleNotificationRequest();
			
			failed_request.setEmail("udhishahi1606@gmail.com");   //we have to make it dynamics
			failed_request.setFcmToken("");
			failed_request.setPhone("8887943623");
			kafkaTemplate.send("handle_payment_failed", failed_request);
			
			return result1;
		}
	}


	@Override
	public PaymentDTO refundPayment(UUID payment_id) throws RazorpayException {
		Payment payment = paymentRepository.getPaymentById(payment_id);
		
		if(payment==null) {
			throw new RuntimeException("The payment id passed is wrong. Please check and pass correct payment id");
		}
		
		if(payment.getStatus() != PaymentStatus.SUCCESS) {
			throw new RuntimeException("The payment is not done successfully. So we are denied the refund request");
		}
		
		RazorpayClient razorpay  = new RazorpayClient(AppConstant.RAZORPAY_KEY, AppConstant.RAZORPAY_SECRET);
		
		JSONObject refundRequest = new JSONObject();
		
		refundRequest.put("payment_id", payment.getRazorpayPaymentId());
		
		refundRequest.put("amount", payment.getAmount().multiply(new BigDecimal(100)));
		
		razorpay.payments.refund(refundRequest);
		
		payment.setStatus(PaymentStatus.REFUNDED);
		
		Payment refundPayment  = paymentRepository.save(payment);
		
		PaymentDTO result = paymentMapper.entityToDto(refundPayment);
		
		return result;
		
	}


	@Override
	public List<Payment> getListOfPaymentOfOrder(UUID order_id) {
		
		return paymentRepository.findByOrder_Id(order_id);
	}


	@Override
	public PaymentDTO getSuccessPaymentList(UUID order_id) {
		
		OrderHistory history = orderHistoryRepository.findByOrderId(order_id);
		
		if(history == null) {
			return null;
		}
		Payment payment = paymentRepository.findByOrder_IdAndStatus(order_id, PaymentStatus.SUCCESS);
		
		PaymentDTO result = paymentMapper.entityToDto(payment);
		
		return result;
	}


	@Override
	public List<Payment> getPaymentByStatus(PaymentStatus status) {
		
		return paymentRepository.findByStatus(status);
	}


	@Override
	public BigDecimal getTotalEarningsForRestaurant(UUID restaurantId) {
		
		return paymentRepository.getTotalEarningsForRestaurant(restaurantId);
	}


	@Override
	public PaymentDTO retryPayment(UUID orderId) throws RazorpayException {
		
		    // Step 1: Fetch the order and ensure it's not already paid
		    OrderDTO order = orderService.getOrderById(orderId);
		    com.order.track.core.entity.Order orderEntity = orderUsecase.getOrderById(orderId);

		    List<Payment> existingPayments = paymentRepository.findByOrder_Id(orderId);

		    boolean alreadyPaid = existingPayments.stream()
		        .anyMatch(p -> p.getStatus() == PaymentStatus.SUCCESS);

		    if (alreadyPaid) {
		        throw new IllegalStateException("Payment already successful. Retry not allowed.");
		    }

		    
		    existingPayments.forEach(p -> {
		        if (p.getStatus() == PaymentStatus.PENDING || p.getStatus() == PaymentStatus.FAILED) {
		            p.setStatus(PaymentStatus.RETRY_ATTEMPT);
		        }
		    });
		    paymentRepository.saveAll(existingPayments);

		    // Step 2: Initiate a new payment (same as your initiatePayment)
		    RazorpayClient razorpay = new RazorpayClient(AppConstant.RAZORPAY_KEY, AppConstant.RAZORPAY_SECRET);

		    JSONObject options = new JSONObject();
		    options.put("amount", order.getTotalPrice().multiply(new BigDecimal(100)));
		    options.put("currency", "INR");
		    options.put("receipt", orderId.toString());
		    options.put("payment_capture", 1);

		    com.razorpay.Order razorpayOrder = razorpay.orders.create(options);

		    Payment payment = Payment.builder()
		            .order(orderEntity)
		            .amount(order.getTotalPrice())
		            .currency(razorpayOrder.get("currency"))
		            .razorpayOrderId(razorpayOrder.get("id"))
		            .status(PaymentStatus.PENDING)
		            .build();

		    paymentRepository.save(payment);

		    return paymentMapper.entityToDto(payment);
		
	}
}
