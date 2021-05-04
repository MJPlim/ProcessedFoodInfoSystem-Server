package com.plim.plimserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserInfoResponse {

    private String name;

    private LocalDate birth;

    private String address;

}
