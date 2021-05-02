package com.plim.plimserver.domain.advertisement.exception;

import java.util.NoSuchElementException;

public class NoAdvertisementFoodDetailException extends NoSuchElementException {
    public NoAdvertisementFoodDetailException(AdvertisementExceptionMessage m) {
        super(m.getMessage());
    }
}
