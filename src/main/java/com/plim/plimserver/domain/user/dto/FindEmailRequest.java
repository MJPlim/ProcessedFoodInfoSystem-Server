package com.plim.plimserver.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class FindEmailRequest {

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String email;
}
