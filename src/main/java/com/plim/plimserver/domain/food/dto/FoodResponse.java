package com.plim.plimserver.domain.food.dto;

import com.plim.plimserver.domain.food.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FoodResponse {

    private final Long foodId;
    private final String foodName;
    private final String category;
    private final String manufacturerName;
    private final String foodImageAddress;
    private final String foodMeteImageAddress;

    public static FoodResponse of(Food food) {
        return new FoodResponse(food.getId(), food.getFoodName(), food.getCategory(), food.getManufacturerName(),
                food.getFoodImage().getFoodImageAddress(), food.getFoodImage().getFoodMeteImageAddress());
    }

}
