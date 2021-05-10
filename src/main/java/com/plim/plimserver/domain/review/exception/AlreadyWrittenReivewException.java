package com.plim.plimserver.domain.review.exception;

public class AlreadyWrittenReivewException extends IllegalArgumentException {
    public AlreadyWrittenReivewException(ReviewExceptionMessage message) {
        super(message.getMessage());
    }
}
