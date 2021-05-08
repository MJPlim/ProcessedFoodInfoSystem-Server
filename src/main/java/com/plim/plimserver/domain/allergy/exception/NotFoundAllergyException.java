package com.plim.plimserver.domain.allergy.exception;

public class NotFoundAllergyException extends IllegalArgumentException {
	
	public NotFoundAllergyException(AllergyExceptionMessage m) {
		super(m.getMessage());
	}
}
