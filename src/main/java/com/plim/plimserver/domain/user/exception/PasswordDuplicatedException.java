package com.plim.plimserver.domain.user.exception;

public class PasswordDuplicatedException extends IllegalArgumentException {

    public PasswordDuplicatedException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
