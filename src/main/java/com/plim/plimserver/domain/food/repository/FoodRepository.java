package com.plim.plimserver.domain.food.repository;

import com.plim.plimserver.domain.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom{

    List<Food> findAllByFoodNameContaining(String foodName);
    
    @Query(value = "Select * from food where food_Name LIKE %:foodName% and allergy_Materials NOT REGEXP :allergies", nativeQuery = true)
    List<Food> findAllByFoodNameContaining(@Param("foodName")String foodName, @Param("allergies")String allergies);

    Page<Food> findAllByFoodNameContaining(String foodName, Pageable page);
    
    @Query(value = "Select * from food where food_Name LIKE %:foodName% and allergy_Materials NOT REGEXP :allergies", nativeQuery = true)
    Page<Food> findAllByFoodNameContaining(@Param("foodName")String foodName, @Param("allergies")String allergies, Pageable page);

    List<Food> findAllByManufacturerNameContaining(String manufacturerName);

    @Query(value = "Select * from food where manufacturer_name LIKE %:manufacturerName% and allergy_Materials NOT REGEXP :allergies", nativeQuery = true)
    List<Food> findAllByManufacturerNameContaining(@Param("manufacturerName")String manufacturerName, @Param("allergies")String allergies);
    
    Page<Food> findAllByManufacturerNameContaining(String manufacturerName, Pageable page);

    @Query(value = "Select * from food where manufacturer_name LIKE %:manufacturerName% and allergy_Materials NOT REGEXP :allergies", nativeQuery = true)
    Page<Food> findAllByManufacturerNameContaining(@Param("manufacturerName")String manufacturerName, @Param("allergies")String allergies, Pageable page);
    
    Optional<Food> findByBarcodeNumber(String barcodeNumber);

    Page<Food> findAllByAllergyMaterialsNotIn(List<String> allergy, Pageable pageable);

    Page<Food> findAllByCategoryContaining(String category, Pageable pageable);

}
