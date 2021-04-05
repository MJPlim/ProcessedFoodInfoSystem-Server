package com.plim.plimserver.domain.user.exception;

public class EmailDuplicateException extends IllegalArgumentException {

    public EmailDuplicateException(String email) {
        super(email + "은 이미 존재하는 E-Mail 입니다.");
    }

}
