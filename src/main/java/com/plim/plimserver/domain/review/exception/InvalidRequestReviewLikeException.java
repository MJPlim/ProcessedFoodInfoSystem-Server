package com.plim.plimserver.domain.review.exception;

public class InvalidRequestReviewLikeException extends IllegalArgumentException{

	public InvalidRequestReviewLikeException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}
}
