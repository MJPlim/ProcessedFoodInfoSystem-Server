package com.plim.plimserver.domain.allergy.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AllergyExceptionMessage {

	NOT_FOUND_ALLERGY_EXCEPTION_MESSAGE("알러지 정보를 찾을 수 없습니다."),
	DUPLICATED_CREATE_USER_ALLERGY_EXCEPTION_MESSAGE("중복된 알러지 생성요청입니다.");

	
	private final String message;
}
