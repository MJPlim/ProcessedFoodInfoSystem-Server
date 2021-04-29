package com.plim.plimserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyPasswordResponse {
	
	private final String message;
	
}
