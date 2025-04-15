package com.food.restaurant.core.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.restaurant.core.entity.Categories;

public interface CategoriesReposiotory extends JpaRepository<Categories, UUID>{

	Optional<Categories> findByCategoryContainingIgnoreCase(String query);

}
