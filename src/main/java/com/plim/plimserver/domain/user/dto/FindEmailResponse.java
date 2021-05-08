package com.plim.plimserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
@Builder
public class FindEmailResponse {

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private final String secondEmail;
}
