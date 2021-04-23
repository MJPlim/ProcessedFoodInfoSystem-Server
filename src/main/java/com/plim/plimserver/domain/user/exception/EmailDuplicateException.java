package com.plim.plimserver.domain.user.exception;

public class EmailDuplicateException extends IllegalArgumentException {

    public EmailDuplicateException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
