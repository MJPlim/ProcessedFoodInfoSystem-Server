package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.food.dto.FoodResponse;

import java.util.ArrayList;

public interface FoodService {
    ArrayList<FoodResponse> findFoodByFoodName(String foodName, int pageNo);
    ArrayList<FoodResponse> findFoodByBsshName(String bsshName, int pageNo);
}
