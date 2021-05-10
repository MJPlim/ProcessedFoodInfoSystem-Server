package com.plim.plimserver.domain.review.exception;

public class NotApproachReviewException extends IllegalArgumentException{
	public NotApproachReviewException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}
}
