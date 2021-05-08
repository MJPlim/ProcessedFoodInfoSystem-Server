package com.plim.plimserver.domain.food.repository;

import com.plim.plimserver.domain.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>{

    List<Food> findAllByFoodNameContaining(String foodName);

    Page<Food> findAllByFoodNameContaining(String foodName, Pageable page);

    List<Food> findAllByManufacturerNameContaining(String manufacturerName);

    Page<Food> findAllByManufacturerNameContaining(String manufacturerName, Pageable page);

    Optional<Food> findByBarcodeNumber(String barcodeNumber);

}
