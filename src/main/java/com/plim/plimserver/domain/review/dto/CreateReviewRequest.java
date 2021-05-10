package com.plim.plimserver.domain.review.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CreateReviewRequest {

	@NotNull(message = "제품 아이디를 입력해주세요.")
	private Long foodId;

	@Min(value = 1, message = "리뷰 점수는 1점 이상이어야 합니다.")
	@Max(value = 5, message = "리뷰 점수는 5점 이하이어야 합니다.")
	private Integer reviewRating;

	@Length(max = 500, message = "리뷰는 최대 500자까지 작성 가능합니다.")
	private String reviewDescription;

}
