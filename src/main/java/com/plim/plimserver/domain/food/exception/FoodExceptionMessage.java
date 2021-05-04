package com.plim.plimserver.domain.food.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FoodExceptionMessage {

    NO_FOOD_DETAIL_EXCEPTION_MESSAGE("해당 제품이 존재하지 않습니다.");

    private final String message;
}
