package com.plim.plimserver.domain.review.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateReviewResponse {
	private Long foodId;
	private Integer reviewRating;
	private String reviewDescription;

	public static CreateReviewResponse from(CreateReviewRequest dto) {
		return new CreateReviewResponse(dto.getFoodId(), dto.getReviewRating(), dto.getReviewDescription());
	}
}
