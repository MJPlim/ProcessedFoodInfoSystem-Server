package com.plim.plimserver.domain.user.dto;

import com.plim.plimserver.domain.user.domain.UserStateType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WithdrawUserResponse {

    private String email;

    private UserStateType state;

}
