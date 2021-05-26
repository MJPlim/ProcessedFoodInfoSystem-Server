package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.global.dto.Pagination;

import java.util.List;

public interface FoodService {

    FoodDetailResponse getFoodDetail(Long foodId);

    FoodDetailResponse findFoodByBarcode(String barcode);

    Pagination<List<FoodResponse>> findFoodByPaging(int pageNo, int size, String sortElement, String foodName, String manufacturerName, List<String> allergyList);

    Pagination<List<FoodResponse>> findFoodByCategory(String categoryList, int page, String sort, int size);

    //    int makeFoodDatabaseWithoutBarCodeAPI();

}
