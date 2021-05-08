package com.plim.plimserver.domain.allergy.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class CreateUserAllergyRequest {
	private List<String> allergyList;
}
