package com.delivery.cart.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.cart.core.entity.Reviews;

@Repository
public interface ReviewAndRatingRepository extends JpaRepository<Reviews, UUID>{
	
	Reviews findByDeliveryId(UUID deliveryId); 

}
