package com.plim.plimserver.food.service;

import com.plim.plimserver.food.dto.FoodDTO;

import java.util.ArrayList;

public interface FoodService {
    ArrayList<FoodDTO> findFood(String foodName, int pageNo);
}
