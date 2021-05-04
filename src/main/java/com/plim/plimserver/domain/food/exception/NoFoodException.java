package com.plim.plimserver.domain.food.exception;

import java.util.NoSuchElementException;

public class NoFoodException extends NoSuchElementException {
    public NoFoodException(FoodExceptionMessage m) {
        super(m.getMessage());
    }
}
