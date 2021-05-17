package com.plim.plimserver.domain.review.dto;

import com.plim.plimserver.domain.review.domain.Review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadReviewIdResponse {
	private Long reviewId;
	
	private String foodName;
	
	private String manufacturerName;
	
	private String pictureUrl;
	
	private Integer reviewRating;
	
	private String reviewDescription;
	
	public static ReadReviewIdResponse of(Review review) {
		return ReadReviewIdResponse.builder()
				.reviewId(review.getId())
				.foodName(review.getFood().getFoodName())
				.manufacturerName(review.getFood().getManufacturerName())
				.pictureUrl(review.getFood().getFoodImage().getFoodImageAddress())
				.reviewRating(review.getReviewRating())
				.reviewDescription(review.getReviewDescription())
				.build();
	}
}
