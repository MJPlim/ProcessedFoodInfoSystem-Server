package com.plim.plimserver.domain.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.food.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

}
