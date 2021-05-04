package com.plim.plimserver.domain.favorite.dto;

import com.plim.plimserver.domain.food.dto.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FavoriteResponse {
    private final Long favoriteId;
    private final FoodResponse food;
}
