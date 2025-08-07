package com.delivery.cart.core.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.cart.adapter.constant.AppConstant;
import com.delivery.cart.adapter.models.CartDto;
import com.delivery.cart.adapter.models.CreateCartRequestDto;
import com.delivery.cart.adapter.models.GenericResponse;
import com.delivery.cart.adapter.models.RemoveCartItemRequest;
import com.delivery.cart.adapter.models.StatusCode;
import com.delivery.cart.adapter.service.CartService;


@RestController
@RequestMapping(AppConstant.CART_CONTROLLER)
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping(AppConstant.ADD_CART)
	GenericResponse<?> addToCart(
	    @RequestBody CreateCartRequestDto request
	) {
		try {
		
		CartDto cartDto = new CartDto();
		cartDto.setUserId(request.getUserId());
		cartDto.setRestaurantId(request.getRestaurantId());
		cartDto.setRestaunrantName(request.getRestaurantName());
		cartDto.setRestaurantPhotoUrl(request.getRestaurantUrl());
		cartDto.setItems(request.getItem());
		cartDto.setTotalPrice(request.getTotalPrice());
	    cartService.addOrUpdateItem(cartDto);
	    return new GenericResponse<>("Item added to cart", StatusCode.of(HttpStatus.OK), null);
		}catch(Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@GetMapping(AppConstant.GET_CART)
	public GenericResponse<CartDto> getCart(@RequestParam UUID userId){
		try {
		CartDto cartDetails = cartService.getCart(userId);
		return new GenericResponse<>("Cart details fetched successfully!!", StatusCode.of(HttpStatus.OK), cartDetails);
		}catch(Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}

	@PutMapping(AppConstant.REMOVE_CARTITEMS)
	public GenericResponse<?> removeCartItem(@RequestBody RemoveCartItemRequest request){
		try {
		cartService.removeItem(request.getUserId(), request.getFoodItemId());
		return new GenericResponse<>("Cart item has been remove successfully!!", StatusCode.of(HttpStatus.ACCEPTED), null);
		}catch(Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}
	
	@DeleteMapping(AppConstant.CLEAR_CART)
	public GenericResponse<?> clearCart(@PathVariable UUID userId){
		try {
		cartService.clearCart(userId);
		
		return new GenericResponse<>("Cart is cleared successfully!!",StatusCode.of(HttpStatus.OK), null);
		}catch(Exception e) {
			e.printStackTrace();
			return new GenericResponse<>("Something went wrong", StatusCode.of(HttpStatus.INTERNAL_SERVER_ERROR), null);
		}
	}

}
