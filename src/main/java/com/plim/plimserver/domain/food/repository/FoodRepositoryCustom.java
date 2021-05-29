package com.plim.plimserver.domain.food.repository;

import com.plim.plimserver.domain.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodRepositoryCustom {
    Page<Food> search(String sortElement, String foodName, String manufacturerName, List<String> allergyList, Pageable pageable);

    Page<Food> findByWideCategory(String[] categories, Pageable pageable);
}
