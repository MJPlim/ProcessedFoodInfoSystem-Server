package com.plim.plimserver.domain.food.exception;

import java.util.NoSuchElementException;

public class NoFoodListException extends NoSuchElementException {
    public NoFoodListException(FoodExceptionMessage m) {
        super(m.getMessage());
    }
}
