package com.plim.plimserver.domain.allergy.service;

import java.util.List;

import com.plim.plimserver.domain.allergy.domain.UserAllergy;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

public interface AllergyService {

	public UserAllergy saveUserAllergy(PrincipalDetails principal, List<String> allergyList);

	public List<String> findUserAllergy(PrincipalDetails principal);

}
