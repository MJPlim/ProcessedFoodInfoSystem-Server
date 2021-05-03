package com.plim.plimserver.domain.advertisement.dto;

import com.plim.plimserver.domain.food.dto.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdvertisementResponse {
    private final Long id;
    private final FoodResponse food;
}
