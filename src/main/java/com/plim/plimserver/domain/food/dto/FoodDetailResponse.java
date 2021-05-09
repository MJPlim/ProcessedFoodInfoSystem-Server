package com.plim.plimserver.domain.food.dto;

import com.plim.plimserver.domain.food.domain.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodDetailResponse {

    private final Long foodId;
    private final String foodName;
    private final String category;
    private final String manufacturerName;
    private final String foodImageAddress;
    private final String foodMeteImageAddress;
    private final String materials;
    private final String nutrient;
    private final String allergyMaterials;
    private final Long viewCount;

    public static FoodDetailResponse from(Food food) {
        return FoodDetailResponse.builder()
                .foodId(food.getId())
                .foodName(food.getFoodName())
                .category(food.getCategory())
                .manufacturerName(food.getManufacturerName())
                .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                .materials(food.getFoodDetail().getMaterials())
                .nutrient(food.getFoodDetail().getNutrient())
                .allergyMaterials(food.getAllergyMaterials())
                .viewCount(food.getViewCount())
                .build();
    }

}
