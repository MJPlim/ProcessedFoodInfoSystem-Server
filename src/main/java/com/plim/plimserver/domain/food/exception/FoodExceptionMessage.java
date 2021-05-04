package com.plim.plimserver.domain.food.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodExceptionMessage {

    NO_FOOD_EXCEPTION_MESSAGE("해당하는 제품이 없습니다."),
    NO_FOOD_DETAIL_EXCEPTION_MESSAGE("해당 제품의 상세정보가 없습니다.");

    private final String message;
}
