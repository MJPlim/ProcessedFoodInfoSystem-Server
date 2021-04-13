package com.plim.plimserver.global.domain.mail.exception;

public class AuthCodeMismatchException extends IllegalArgumentException{

    public AuthCodeMismatchException(String s) {
        super(s);
    }

}
