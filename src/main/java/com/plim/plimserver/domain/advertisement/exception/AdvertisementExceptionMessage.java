package com.plim.plimserver.domain.advertisement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdvertisementExceptionMessage {

    NO_ADVERTISEMENT_FOOD_DETAIL_EXCEPTION_MESSAGE("해당하는 광고 제품이 없습니다.");

    private final String message;
}
