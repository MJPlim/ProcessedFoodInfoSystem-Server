package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.food.dto.FoodDTO;

import java.util.ArrayList;

public interface FoodService {
    ArrayList<FoodDTO> findFoodByFoodName(String foodName, int pageNo);
    ArrayList<FoodDTO> findFoodByBsshName(String bsshName, int pageNo);
}
