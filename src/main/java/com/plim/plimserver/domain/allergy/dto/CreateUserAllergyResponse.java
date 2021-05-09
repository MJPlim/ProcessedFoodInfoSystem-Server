package com.plim.plimserver.domain.allergy.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserAllergyResponse {
	private String message;
	private List<String> allergyList;
}
