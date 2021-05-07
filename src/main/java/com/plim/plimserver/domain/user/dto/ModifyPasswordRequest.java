package com.plim.plimserver.domain.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class ModifyPasswordRequest {

    @NotBlank(message = "기존 패스워드를 입력해주세요.")
    @Size(min = 7, max = 20, message = "패스워드는 7글자 이상 20글자 이하여야 합니다.")
    private String beforePassword;

    @NotBlank(message = "새로운 패스워드를 입력해주세요.")
    @Size(min = 7, max = 20, message = "패스워드는 7글자 이상 20글자 이하여야 합니다.")
    private String afterPassword;

}
