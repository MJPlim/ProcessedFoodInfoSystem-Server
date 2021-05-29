package com.plim.plimserver.domain.food.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodCategory {

    SNACK("간식"),
    TEA("음료/차"),
    DAIRY("유제품"),
    AGRICULTURAL_FISHERY("농수산물"),
    KIMCHI("김치"),
    SEASONING("조미료"),
    INSTANT("즉석조리식품"),
    ETC("기타");

    private final String message;
}
