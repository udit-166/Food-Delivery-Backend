package com.order.track.core.controller;


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
import com.order.track.adapter.model.CancelOrderRequestDto;
import com.order.track.adapter.model.CountOrderResponse;
import com.order.track.adapter.model.GenericResponse;
import com.order.track.adapter.model.OrderDTO;
import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.model.OrderSummaryDTO;
import com.order.track.adapter.model.RequestForCancellationDto;
import com.order.track.adapter.model.ReviewPendingResponse;
import com.order.track.adapter.model.StatusCode;
import com.order.track.adapter.model.UpdateOrderRequestDto;
import com.order.track.adapter.service.OrderService;

@RestController
@RequestMapping(AppConstant.ORDER_CONTROLLER)
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(AppConstant.PLACE_ORDER)
	GenericResponse<OrderDTO> placeOrder(@RequestBody OrderDTO order){
		try {
			return new GenericResponse<>("Order Placed successfully!!", StatusCode.of(HttpStatus.OK), orderService.placeOrder(order) );
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_ORDER_BY_ID)
	GenericResponse<OrderDTO> getOrderById(@PathVariable UUID order_id){
		try {
			return new GenericResponse<>("Order details fetched successfully!!", StatusCode.of(HttpStatus.OK), orderService.getOrderById(order_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_ORDER_BY_CUSTOMER_ID)
	GenericResponse<OrderDTO> getOrderByCustomerId(@PathVariable UUID customer_id){
		try {
			return new GenericResponse<>("Order details fetched successfully!!",StatusCode.of(HttpStatus.OK), orderService.getOrderByCustomerId(customer_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_ORDER_BY_RESTAURANT_ID)
	GenericResponse<OrderDTO> getOrderByRestaurant(@PathVariable UUID restaurant_id){
		try {
			return new GenericResponse<>("Order details fetched successfully!!",StatusCode.of(HttpStatus.OK), orderService.getOrderByRestaurantId(restaurant_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.UPDATE_ORDER_STATUS)
	GenericResponse<OrderDTO> updateOrderStatus(@RequestBody UpdateOrderRequestDto request){
		try {
			return new GenericResponse<>("Order status has been updated successfully!!",StatusCode.of(HttpStatus.OK), orderService.updateOrderStatus(request.getRestaurant_id(), request.getOrder_id(), request.getStatus()));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.CANCEL_ORDER)
	GenericResponse<?> cancelOrder(@RequestBody CancelOrderRequestDto request){
		try {
			boolean result = orderService.cancelOrder(request.getRestaurant_id(), request.getAdmin_id(), request.getOrder_id());
			if(result==false) {
				return new GenericResponse<>("The order is unable to cancel right now!!", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			return new GenericResponse<>("The order has been cancel successfully", StatusCode.of(HttpStatus.OK), null);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@PutMapping(AppConstant.REQUEST_FOR_CANCELLATION_OF_ORDER)
	GenericResponse<String> requestForCancellation(@RequestBody RequestForCancellationDto request){
		try {
			boolean result = orderService.requestCancellationOfOrder(request.getCustomer_id(), request.getOrder_id());
			if(result==false) {
				return new GenericResponse<>("The request for cancellation has not been send successfully", StatusCode.of(HttpStatus.BAD_REQUEST), null);
			}
			return new GenericResponse<>("Your request for cancellation of order has been send succesffuly. We will update you.", StatusCode.of(HttpStatus.OK), null);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.TRACK_YOUR_ORDER)
	GenericResponse<String> trackOrder(@PathVariable UUID order_id){
		try {
			return new GenericResponse<>("Track Order fetched succssfully!!",StatusCode.of(HttpStatus.OK), orderService.trackOrder(order_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.ORDER_SUMMARY)
	GenericResponse<OrderSummaryDTO> orderSummary(@RequestParam UUID order_id){
		try {
			return new GenericResponse<>("Order summary fetched successfully!!",StatusCode.of(HttpStatus.OK), orderService.orderSummary(order_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_COUNT_OF_ORDER_BY_CUSTOMER_ID)
	GenericResponse<CountOrderResponse> countOrderByCustomerId(@PathVariable UUID customer_id){
		try {
			return new GenericResponse<>("Fetched count for order by customer id successfully!!",StatusCode.of(HttpStatus.OK), orderService.countOrderByCustomerId(customer_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	
	@GetMapping(AppConstant.GET_COUNT_OF_ORDER_BY_RESTAURANT_ID)
	GenericResponse<CountOrderResponse> countOrderByRestaurantId(@PathVariable UUID restaurant_id){
		try {
			return new GenericResponse<>("Fetched count for order by restaurant id successfully!!",StatusCode.of(HttpStatus.OK),  orderService.countOrderByRestaurantId(restaurant_id));
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_PENDING_REVIEW_REQUEST)
	GenericResponse<ReviewPendingResponse> pendingReview(@PathVariable UUID customer_id){
		try {
			ReviewPendingResponse result = orderService.getLastFiveDaysReviewPending(customer_id);
			return new GenericResponse<>("Pending review for an order fetched successfully!!", StatusCode.of(HttpStatus.OK), result);
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong!!",StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
}
