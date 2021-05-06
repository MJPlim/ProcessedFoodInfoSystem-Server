package com.plim.plimserver.domain.review.dto;

import java.sql.Timestamp;

import com.plim.plimserver.domain.review.domain.ReviewStateType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadReviewResponse {
	private Long reviewId;
	private String userName;
	private Long foodId;
	private float reviewRating;
	private String reviewDescription;
	private Timestamp reviewCreatedDate;
	private Timestamp reviewModifiedDate;
	private ReviewStateType state;
	private boolean userCheck;
	private boolean userLikeCheck;
	private Integer likeCount;
}
