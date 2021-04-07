package com.plim.plimserver.domain.user.exception;

public class PasswordMismatchException extends IllegalArgumentException {
    public PasswordMismatchException(String s) {
        super(s);
    }
}
