package com.plim.plimserver.domain.review.dto;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.domain.ReviewStateType;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadReviewResponse {
	private Long reviewId;
	private String userName;
	private Long foodId;
	private String foodName;
	private Integer reviewRating;
	private String reviewDescription;
	private Timestamp reviewCreatedDate;
	private Timestamp reviewModifiedDate;
	private ReviewStateType state;
	private boolean userCheck;
	private boolean userLikeCheck;
	private Integer likeCount;

	public static ReadReviewResponse of(Review review, Long foodId, String foodName, boolean userCheck, boolean userLikeCheck, int likeCount) {
		return ReadReviewResponse.builder()
				.reviewId(review.getId())
				.userName(review.getUser().getName())
				.foodId(foodId)
				.foodName(foodName)
				.reviewRating(review.getReviewRating())
				.reviewDescription(review.getReviewDescription())
				.reviewCreatedDate(review.getReviewCreatedDate())
				.reviewModifiedDate(review.getReviewModifiedDate())
				.state(review.getState())
				.userCheck(userCheck)
				.userLikeCheck(userLikeCheck)
				.likeCount(likeCount)
				.build();
	}
}
