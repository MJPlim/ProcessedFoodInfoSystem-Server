package com.plim.plimserver.domain.user.exception;

public class SecondEmailDuplicateException extends IllegalArgumentException {

    public SecondEmailDuplicateException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
