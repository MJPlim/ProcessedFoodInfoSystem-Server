package com.plim.plimserver.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FindFoodBySortingResponse {
    private final int pageNo;
    private final int pageSize;
    private final int maxPage;
    private final int totalDataCount;
    private final List<FoodResponse> resultList;
}
