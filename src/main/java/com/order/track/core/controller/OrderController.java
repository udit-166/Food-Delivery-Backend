package com.order.track.core.controller;


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
import com.order.track.adapter.model.OrderDTO;
import com.order.track.adapter.model.OrderStatus;
import com.order.track.adapter.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(AppConstant.PLACE_ORDER)
	ResponseEntity<OrderDTO> placeOrder(@RequestParam OrderDTO order){
		try {
			return new ResponseEntity<>(orderService.placeOrder(order), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.GET_ORDER_BY_ID)
	ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID order_id){
		try {
			return new ResponseEntity<>(orderService.getOrderById(order_id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.GET_ORDER_BY_CUSTOMER_ID)
	ResponseEntity<OrderDTO> getOrderByCustomerId(@PathVariable UUID Customer_id){
		try {
			return new ResponseEntity<>(orderService.getOrderByCustomerId(Customer_id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.GET_ORDER_BY_RESTAURANT_ID)
	ResponseEntity<OrderDTO> getOrderByRestaurant(@PathVariable UUID restaurant_id){
		try {
			return new ResponseEntity<>(orderService.getOrderByRestaurantId(restaurant_id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstant.UPDATE_ORDER_STATUS)
	ResponseEntity<OrderDTO> updateOrderStatus(@RequestParam UUID restaurant, @RequestParam UUID order_id,@RequestParam OrderStatus status){
		try {
			return new ResponseEntity<>(orderService.updateOrderStatus(restaurant, order_id, status), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstant.CANCEL_ORDER)
	ResponseEntity<?> cancelOrder(@RequestParam UUID restaurant_id,@RequestParam UUID admin_id,@RequestParam UUID order_id){
		try {
			boolean result = orderService.cancelOrder(restaurant_id, admin_id, order_id);
			if(result==false) {
				return new ResponseEntity<>("The order is unable to cancel right now!!", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>("The order has been cancel successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The cancelOrder service is unable to run effeciently", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(AppConstant.REQUEST_FOR_CANCELLATION_OF_ORDER)
	ResponseEntity<String> requestForCancellation(@RequestParam UUID customer_id ,@RequestParam UUID order_id){
		try {
			boolean result = orderService.requestCancellationOfOrder(customer_id, order_id);
			if(result==false) {
				return new ResponseEntity<>("The request for cancellation has not been send successfully", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>("Your request for cancellation of order has been send succesffuly. We will update you.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The requestForCancellation service is unable to run effeciently", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(AppConstant.TRACK_YOUR_ORDER)
	ResponseEntity<String> trackOrder(@PathVariable UUID order_id){
		try {
			return new ResponseEntity<>(orderService.trackOrder(order_id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The trackOrder service is not running and has some error occur!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
