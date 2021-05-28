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
	
	private String category;
	
	private String foodImageAddress;

	public static ReviewRankingResponse from(ReviewSummary reviewSummary) {
		String[] splitCategory = reviewSummary.getFood().getCategory().split("_");
		return ReviewRankingResponse.builder()
				.foodId(reviewSummary.getFood().getId())
				.foodName(reviewSummary.getFood().getFoodName())
				.avgRating(String.format("%.02f", reviewSummary.getAvgRating()))
				.category(splitCategory[0])
				.foodImageAddress(reviewSummary.getFood().getFoodImage().getFoodImageAddress())
				.build();
	}

}
