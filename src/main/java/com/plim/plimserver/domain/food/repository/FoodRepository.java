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

    Optional<Food> findByBarcodeNumber(String barcodeNumber);
    
    @Query("Select f from Food f where f.manufacturerName like '%농심%'")
    Optional<List<Food>> findByManufacturerName(String manufacturerName);

    Page<Food> findAllByCategoryContaining(String category, Pageable pageable);

}
