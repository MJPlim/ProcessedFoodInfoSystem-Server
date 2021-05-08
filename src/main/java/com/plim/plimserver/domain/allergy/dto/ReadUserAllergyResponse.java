package com.plim.plimserver.domain.allergy.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadUserAllergyResponse {
	private List<String> allergyMaterial;
}
