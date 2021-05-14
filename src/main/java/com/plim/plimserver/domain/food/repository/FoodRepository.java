package com.plim.plimserver.domain.food.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plim.plimserver.domain.food.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

    List<Food> findAllByFoodNameContaining(String foodName);
    
    @Query(value = "Select * from food where food_Name LIKE %:foodName% and allergy_Materials NOT REGEXP :allergys", nativeQuery = true)
    List<Food> findAllByFoodNameContaining(@Param("foodName")String foodName, @Param("allergys")String allergys);

    Page<Food> findAllByFoodNameContaining(String foodName, Pageable page);
    
    @Query(value = "Select * from food where food_Name LIKE %:foodName% and allergy_Materials NOT REGEXP :allergys", nativeQuery = true)
    Page<Food> findAllByFoodNameContaining(@Param("foodName")String foodName, @Param("allergys")String allergys, Pageable page);

    List<Food> findAllByManufacturerNameContaining(String manufacturerName);

    @Query(value = "Select * from food where manufacturer_name LIKE %:manufacturerName% and allergy_Materials NOT REGEXP :allergys", nativeQuery = true)
    List<Food> findAllByManufacturerNameContaining(@Param("manufacturerName")String manufacturerName, @Param("allergys")String allergys);
    
    Page<Food> findAllByManufacturerNameContaining(String manufacturerName, Pageable page);

    @Query(value = "Select * from food where manufacturer_name LIKE %:manufacturerName% and allergy_Materials NOT REGEXP :allergys", nativeQuery = true)
    Page<Food> findAllByManufacturerNameContaining(@Param("manufacturerName")String manufacturerName, @Param("allergys")String allergys, Pageable page);
    
    Optional<Food> findByBarcodeNumber(String barcodeNumber);

    Page<Food> findAllByAllergyMaterialsNotIn(List<String> allergy, Pageable pageable);

    Page<Food> findAllByCategoryContaining(String category, Pageable pageable);

}
