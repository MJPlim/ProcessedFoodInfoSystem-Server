package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.food.dto.FindFoodBySortingResponse;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface FoodService {
    ArrayList<FoodResponse> findFoodByFoodName(String foodName);
    ArrayList<FoodResponse> findFoodByManufacturerName(String manufacturerName);

    int makeFoodDatabaseWithoutBarCodeAPI();

    FoodDetailResponse getFoodDetail(Long foodId);

    FoodDetailResponse findFoodByBarcode(String barcode);

    FindFoodBySortingResponse findFoodByPaging(int pageNo, int size, String sortElement, String foodName, String manufacturerName);
}
