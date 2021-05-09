package com.plim.plimserver.domain.food.dto;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.domain.Review;
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
    private final int reviewCount;
    private final String reviewRate;

    public static FoodResponse from(Food food) {
        return FoodResponse.builder()
                .foodId(food.getId())
                .foodName(food.getFoodName())
                .category(food.getCategory())
                .manufacturerName(food.getManufacturerName())
                .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                .reviewRate(String.format("%.2f",
                        food.getReviewList().stream()
                        .mapToInt(Review::getReviewRating).average().orElse(0)))
                .build();
    }

}
