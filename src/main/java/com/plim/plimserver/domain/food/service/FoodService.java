package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.food.dto.FindFoodBySortingResponse;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.global.dto.Pagination;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface FoodService {

    FoodDetailResponse getFoodDetail(Long foodId);

    FoodDetailResponse findFoodByBarcode(String barcode);

    FindFoodBySortingResponse findFoodByPaging(int pageNo, int size, String sortElement, String foodName, String manufacturerName);

    Pagination<List<FoodResponse>> findFoodByCategory(String categoryList, Pageable pageable);

    //    int makeFoodDatabaseWithoutBarCodeAPI();

}
