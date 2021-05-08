package com.plim.plimserver.domain.allergy.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plim.plimserver.domain.allergy.dto.CreateUserAllergyRequest;
import com.plim.plimserver.domain.allergy.dto.CreateUserAllergyResponse;
import com.plim.plimserver.domain.allergy.service.AllergyService;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(tags = {"Allergy"})
@RequiredArgsConstructor
@RestController
public class AllergyController {
	
	private final AllergyService allergyService;
	
	@PostMapping("api/v1/user/createUserAllergy")
	public ResponseEntity<CreateUserAllergyResponse> createUserAllergy(@AuthenticationPrincipal PrincipalDetails principal,
			@RequestBody CreateUserAllergyRequest dto){
		allergyService.saveUserAllergy(principal, dto.getAllergyList());
		
		return ResponseEntity.ok(CreateUserAllergyResponse.builder()
				.message("유저알러지정보가 추가되었습니다.")
				.allergyList(dto.getAllergyList())
				.build());
	}
	
	@GetMapping("api/v1/user/readUserAllergy")
	public Map<String, Object> readUserAllergy(@AuthenticationPrincipal PrincipalDetails principal){
		List<String> userAllergyList = allergyService.findUserAllergy(principal);
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("userAllergyMaterials", userAllergyList);
		return returnMap;
	}
	
}
