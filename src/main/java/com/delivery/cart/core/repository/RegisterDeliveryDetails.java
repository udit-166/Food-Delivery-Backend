package com.delivery.cart.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.cart.core.entity.DeliveryPartnerDetails;

@Repository
public interface RegisterDeliveryDetails extends JpaRepository<DeliveryPartnerDetails, UUID>{

}
