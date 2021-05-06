package com.plim.plimserver.domain.food.dto;

import com.plim.plimserver.domain.favorite.domain.Favorite;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
    private final List<Review> reviewList;
    private final List<Favorite> favoriteList;

    public static FoodDetailResponse of(Food food) {
        return new FoodDetailResponse(
                food.getId(), food.getFoodName(), food.getCategory(), food.getManufacturerName(),
                food.getFoodImage().getFoodImageAddress(), food.getFoodImage().getFoodMeteImageAddress(),
                food.getFoodDetail().getMaterials(), food.getFoodDetail().getNutrient(), food.getAllergyMaterials(),
                food.getViewCount(), food.getReviewList(), food.getFavoriteList()
        );
    }

}
