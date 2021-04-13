package com.plim.plimserver.domain.user.exception;

public class EmailDuplicateException extends IllegalArgumentException {

    public EmailDuplicateException(String email) {
        super(email + "은(는) 이미 가입된 이메일 주소입니다.");
    }

}
