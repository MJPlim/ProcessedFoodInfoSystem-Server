package com.plim.plimserver.domain.review.service;

import java.util.List;
import java.util.Map;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.domain.ReviewLike;
import com.plim.plimserver.domain.review.dto.CreateReviewRequest;
import com.plim.plimserver.domain.review.dto.DeleteReviewRequest;
import com.plim.plimserver.domain.review.dto.ReadReviewRequest;
import com.plim.plimserver.domain.review.dto.ReadReviewResponse;
import com.plim.plimserver.domain.review.dto.ReviewRankingResponse;
import com.plim.plimserver.domain.review.dto.UpdateReviewLikeRequest;
import com.plim.plimserver.domain.review.dto.UpdateReviewRequest;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

public interface ReviewService {

	public Review saveReview(PrincipalDetails principal, CreateReviewRequest dto);

	public List<ReadReviewResponse> findReview(Long foodId, Integer pageNum);

	public List<ReadReviewResponse> findReviewByUserId(PrincipalDetails principal);

	public List<ReadReviewResponse> findReviewByUserIdANDFoodID(PrincipalDetails principal, Long foodId, Integer pageNum);

	public Review changeReview(PrincipalDetails principal, UpdateReviewRequest dto);

	public ReviewLike changeReviewLike(PrincipalDetails principal, UpdateReviewLikeRequest dto);

	public Review removeReview(PrincipalDetails principal, DeleteReviewRequest dto);
	
	public List<ReviewRankingResponse> rankedReview();

	public Map<String, Integer> findReviewTotalCount(Long foodId);



	
	
}
