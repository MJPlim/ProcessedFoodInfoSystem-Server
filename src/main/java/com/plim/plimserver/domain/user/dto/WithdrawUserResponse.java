package com.plim.plimserver.domain.user.dto;

import com.plim.plimserver.domain.user.domain.StateType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WithdrawUserResponse {

    private String email;

    private StateType state;

}
