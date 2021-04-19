package com.plim.plimserver.domain.user.dto;

import javax.validation.constraints.Email;
import lombok.Getter;

@Getter
public class FindPasswordRequest {

    @Email
    private String email;

}
