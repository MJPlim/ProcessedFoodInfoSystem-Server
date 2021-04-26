package com.plim.plimserver.domain.food.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodExceptionMessage {

    NO_FOOD_DETAIL_EXCEPTION_MESSAGE("해당하는 제품이 없습니다.");

    private final String message;
}
