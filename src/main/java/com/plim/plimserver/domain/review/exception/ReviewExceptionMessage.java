package com.plim.plimserver.domain.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewExceptionMessage {
	
	INVALID_REQUEST_REVIEWLIKE_EXCEPTION_MESSAGE("잘못된 좋아요 요청입니다."),
	NOT_SUCH_REVIEW_EXCEPTION_MESSAGE("해당 리뷰가 없습니다."),
	DELETED_REVIEW_EXCEPTION_MESSAGE("이미 삭제된 리뷰입니다."),
	OUT_COUNT_INPUT_REVIEW_EXCEPTION_MESSAGE("별점의 범위를 넘어섰습니다."),
	NOT_APPROACH_REVIEW_EXCEPTION_MESSAGE("다른 사용자가 해당 리뷰에 접근할 수 없습니다."),
	NOT_FOUND_PAGE_EXCEPTION_MESSAGE("페이지 값이 정상적이지 않습니다."),
	ALREADY_WRITTEN_REVIEW_EXCEPTION_MESSAGE("리뷰는 하나만 작성이 가능합니다.");

	private final String message;

}
