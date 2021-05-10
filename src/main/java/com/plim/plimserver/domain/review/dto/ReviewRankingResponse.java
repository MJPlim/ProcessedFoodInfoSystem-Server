package com.plim.plimserver.domain.review.dto;

import com.plim.plimserver.domain.review.domain.ReviewSummary;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewRankingResponse {
	
	private Long foodId;
	
	private String foodName;
	
	private String avgRating;

	public static ReviewRankingResponse from(ReviewSummary reviewSummary) {
		return ReviewRankingResponse.builder()
				.foodId(reviewSummary.getFood().getId())
				.foodName(reviewSummary.getFood().getFoodName())
				.avgRating(String.format("%.02f", reviewSummary.getAvgRating()))
				.build();
	}

}
