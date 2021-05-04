package com.plim.plimserver.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewRankingResponse {
	
	private Long foodId;
	
	private String foodName;
	
	private float avgRating;
}
