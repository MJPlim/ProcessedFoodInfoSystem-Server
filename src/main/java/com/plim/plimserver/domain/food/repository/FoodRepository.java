package com.plim.plimserver.domain.food.repository;

import com.plim.plimserver.domain.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>{

    List<Food> findAllByFoodNameContaining(String foodName);

    List<Food> findAllByManufacturerNameContaining(String manufacturerName);

    Optional<Food> findByBarcodeNumber(String barcodeNumber);

}
