package com.plim.plimserver.domain.review.service;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.domain.ReviewLike;
import com.plim.plimserver.domain.review.domain.ReviewSummary;
import com.plim.plimserver.domain.review.dto.*;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import java.util.List;
import java.util.Map;

public interface ReviewService {

	public void saveReview(PrincipalDetails principal, CreateReviewRequest dto);

	public List<ReadReviewResponse> findReview(Long foodId, Integer pageNum);

	public List<ReadReviewResponse> findReviewByUserId(PrincipalDetails principal);

	public List<ReadReviewResponse> findReviewByUserIdANDFoodID(PrincipalDetails principal, Long foodId, Integer pageNum);

	public Review changeReview(PrincipalDetails principal, UpdateReviewRequest dto);

	public ReviewLike changeReviewLike(PrincipalDetails principal, UpdateReviewLikeRequest dto);

	public Review removeReview(PrincipalDetails principal, DeleteReviewRequest dto);
	
	public List<ReviewRankingResponse> rankedReview();

	public ReadSummaryResponse findReviewSummary(Long foodId);

	public ReadReviewIdResponse findReviewByReviewId(PrincipalDetails principal, Long reviewId);

}
