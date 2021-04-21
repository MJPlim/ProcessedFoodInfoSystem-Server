package com.plim.plimserver.domain.user.exception;

public class EmailNotVerifedException extends IllegalArgumentException {

    public EmailNotVerifedException(UserExceptionMessage m) {
        super(m.getMessage());
    }

}
