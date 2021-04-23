package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;

import java.util.ArrayList;

public interface FoodService {
    ArrayList<FoodResponse> findFoodByFoodName(String foodName);
    ArrayList<FoodResponse> findFoodByManufacturerName(String manufacturerName);

    int makeFoodDatabaseWithoutBarCode();

    FoodDetailResponse getFoodDetail(Long foodId);
}
