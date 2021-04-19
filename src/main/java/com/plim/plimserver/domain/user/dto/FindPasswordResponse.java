package com.plim.plimserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class FindPasswordResponse {

    private final String email;

}
