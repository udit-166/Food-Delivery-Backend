package com.delivery.cart.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.cart.core.entity.Deliveries;

@Repository
public interface DeliveriesRepository extends JpaRepository<Deliveries, UUID>{

}
