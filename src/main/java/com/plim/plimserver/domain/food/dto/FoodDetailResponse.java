package com.plim.plimserver.domain.food.dto;

import com.plim.plimserver.domain.food.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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

    public static FoodDetailResponse of(Food food) {
        return new FoodDetailResponse(
                food.getId(), food.getFoodName(), food.getCategory(), food.getManufacturerName(),
                food.getFoodImage().getFoodImageAddress(), food.getFoodImage().getFoodMeteImageAddress(),
                food.getFoodDetail().getMaterials(), food.getFoodDetail().getNutrient(), food.getAllergyMaterials(),
                food.getViewCount());
    }

}
