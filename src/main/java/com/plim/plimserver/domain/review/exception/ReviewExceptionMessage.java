package com.plim.plimserver.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionMessage {
	
	NOT_SUCH_REVIEW_EXCEPTION_MESSAGE("해당 리뷰가 없습니다."),
	DELETED_REVIEW_EXCEPTION_MESSAGE("이미 삭제된 리뷰입니다."),
	OUT_COUNT_INPUT_REVIEW_EXCEPTION_MESSAGE("별점의 범위를 넘어섰습니다.");
	
	private final String message;
}
