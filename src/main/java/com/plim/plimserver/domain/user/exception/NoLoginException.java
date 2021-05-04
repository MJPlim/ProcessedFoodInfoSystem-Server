package com.plim.plimserver.domain.user.exception;

public class NoLoginException extends IllegalArgumentException {

    public NoLoginException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
