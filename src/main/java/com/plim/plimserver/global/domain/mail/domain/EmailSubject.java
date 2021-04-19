package com.plim.plimserver.global.domain.mail.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailSubject {

    EMAIL_AUTH_REQUEST("[KATI] 회원가입 인증 메일"),
    FIND_PASSWORD_REQUEST("[KATI] 임시 비밀번호 안내 메일");

    @Getter
    private final String subject;

}
