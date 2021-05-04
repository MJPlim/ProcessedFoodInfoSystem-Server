package com.plim.plimserver.domain.review.exception;

public class DeletedReviewException extends IllegalArgumentException{

	public DeletedReviewException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}
}
