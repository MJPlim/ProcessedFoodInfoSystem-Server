package com.plim.plimserver.domain.food.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodCategory {

    SNACK("간식"),
    DRINK("음료/차"),
    DAIRY("유제품"),
    MEAT("육류"),
    AGRICULTURAL_FISHERY("농수산물"),
    KIMCHI("김치"),
    SEASONING("조미료"),
    INSTANT("즉석조리식품"),
    MATERIAL("식재료"),
    ETC("기타");

    private final String message;
}
