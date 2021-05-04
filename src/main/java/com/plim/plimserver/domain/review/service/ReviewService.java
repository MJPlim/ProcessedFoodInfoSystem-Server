package com.plim.plimserver.domain.review.service;

import java.util.List;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.dto.CreateReviewRequest;
import com.plim.plimserver.domain.review.dto.DeleteReviewRequest;
import com.plim.plimserver.domain.review.dto.ReadReviewRequest;
import com.plim.plimserver.domain.review.dto.ReadReviewResponse;
import com.plim.plimserver.domain.review.dto.ReviewRankingResponse;
import com.plim.plimserver.domain.review.dto.UpdateReviewRequest;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

public interface ReviewService {

	public Review saveReview(PrincipalDetails principal, CreateReviewRequest dto);

	public List<ReadReviewResponse> findReview(ReadReviewRequest dto);

	public Review removeReview(PrincipalDetails principal, DeleteReviewRequest dto);

	public Review changeReview(PrincipalDetails principal, UpdateReviewRequest dto);

	public List<ReadReviewResponse> findReviewByUserId(PrincipalDetails principal);

	public List<ReviewRankingResponse> rankedReview();
	
	
}
