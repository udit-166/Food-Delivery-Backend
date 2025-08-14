package com.food.restaurant.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.restaurant.core.entity.RestaurantReviews;

@Repository
public interface RestaurantRatingRepository extends JpaRepository<RestaurantReviews, UUID>{

}
