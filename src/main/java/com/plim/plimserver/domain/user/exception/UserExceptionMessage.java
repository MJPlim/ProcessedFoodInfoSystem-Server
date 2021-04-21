package com.plim.plimserver.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionMessage {

    PASSWORD_MISMATCH_EXCEPTION_MESSAGE("패스워드가 일치하지 않습니다."),
    USERNAME_NOT_FOUND_EXCEPTION_MESSAGE("해당 유저가 존재하지 않습니다."),
    EMAIL_DUPLICATE_EXCEPTION_MESSAGE("해당 이메일 주소는 이미 회원가입된 이메일 주소입니다."),
    EMAIL_NOT_VERIFIED_EXCEPTION_MESSAGE("아직 이메일 인증이 되지 않았습니다. 이메일 인증을 해주시길 바랍니다."),
    EMAIL_NOT_FOUND_EXCEPTION_MESSAGE("해당 이메일 주소는 회원가입되지 않은 이메일 주소입니다.");

    private final String message;

}
