package com.plim.plimserver.domain.allergy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plim.plimserver.domain.allergy.domain.FoodAllergy;
import com.plim.plimserver.domain.allergy.domain.UserAllergy;
import com.plim.plimserver.domain.allergy.dto.CreateUserAllergyRequest;
import com.plim.plimserver.domain.allergy.dto.CreateUserAllergyResponse;
import com.plim.plimserver.domain.allergy.exception.AllergyExceptionMessage;
import com.plim.plimserver.domain.allergy.exception.DuplicatedCreateUserAllergyException;
import com.plim.plimserver.domain.allergy.exception.NotFoundAllergyException;
import com.plim.plimserver.domain.allergy.repository.FoodAllergyRepository;
import com.plim.plimserver.domain.allergy.repository.UserAllergyRepository;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AllergyServiceImpl implements AllergyService {

	private final UserRepository userRepository;
	private final UserAllergyRepository userAllergyRepository;
	private final FoodAllergyRepository foodallergyRepository;

	@Transactional
	public UserAllergy saveUserAllergy(PrincipalDetails principal, List<String> allergyList) {
		User findUser = userRepository.findByEmail(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(
						UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		
		userAllergyRepository.deleteByUserID(findUser.getId());
		
		UserAllergy userAllergy = null;

		for (String s : allergyList) {
			FoodAllergy findfoodAllergy = foodallergyRepository.findByAllergyMaterial(s)
					.orElseThrow(() -> new NotFoundAllergyException(AllergyExceptionMessage.NOT_FOUND_ALLERGY_EXCEPTION_MESSAGE));
			
			UserAllergy findUserAllergy = userAllergyRepository.findByUserIDandFoodAllergyID(findUser.getId(), findfoodAllergy.getId());
			
			if(findUserAllergy == null) {
				userAllergy = userAllergyRepository.save(UserAllergy.builder()
						.user(findUser)
						.foodAllergy(findfoodAllergy)
						.build());
			}else
				throw new DuplicatedCreateUserAllergyException(AllergyExceptionMessage.DUPLICATED_CREATE_USER_ALLERGY_EXCEPTION_MESSAGE);
		}

		return userAllergy;
	}

	@Transactional(readOnly = true)
	public List<String> findUserAllergy(PrincipalDetails principal) {
		User findUser = userRepository.findByEmail(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(
						UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		List<UserAllergy> userAllergylist = findUser.getUserAllergyList();
		List<String> returnList = new ArrayList<>();
		for(UserAllergy s : userAllergylist) {
			returnList.add(s.getFoodAllergy().getAllergyMaterial());
		}
		
		return returnList;
	}

}
