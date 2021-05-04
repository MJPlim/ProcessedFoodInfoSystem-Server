package com.plim.plimserver.domain.review.dto;

import lombok.Getter;

@Getter
public class CreateReviewRequest {
	
	private Long foodId;
	
	private int reviewRating;
	
	private String reviewDescription;

}
