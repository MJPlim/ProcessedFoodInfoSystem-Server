package com.plim.plimserver.domain.review.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.exception.FoodExceptionMessage;
import com.plim.plimserver.domain.food.exception.NoFoodDetailException;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.domain.ReviewLike;
import com.plim.plimserver.domain.review.domain.ReviewStateType;
import com.plim.plimserver.domain.review.domain.ReviewSummary;
import com.plim.plimserver.domain.review.dto.CreateReviewRequest;
import com.plim.plimserver.domain.review.dto.DeleteReviewRequest;
import com.plim.plimserver.domain.review.dto.ReadReviewResponse;
import com.plim.plimserver.domain.review.dto.ReviewRankingResponse;
import com.plim.plimserver.domain.review.dto.UpdateReviewLikeRequest;
import com.plim.plimserver.domain.review.dto.UpdateReviewRequest;
import com.plim.plimserver.domain.review.etc.DateComparatorASC;
import com.plim.plimserver.domain.review.exception.DeletedReviewException;
import com.plim.plimserver.domain.review.exception.InvalidRequestReviewLikeException;
import com.plim.plimserver.domain.review.exception.NotFoundPageException;
import com.plim.plimserver.domain.review.exception.NotSuchReviewException;
import com.plim.plimserver.domain.review.exception.ReviewExceptionMessage;
import com.plim.plimserver.domain.review.repository.ReviewLikeRepository;
import com.plim.plimserver.domain.review.repository.ReviewRepository;
import com.plim.plimserver.domain.review.repository.ReviewSummaryRepository;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.NotLoginException;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final FoodRepository foodRepository;
	private final ReviewSummaryRepository reviewSummaryRepository;
	private final ReviewLikeRepository reviewLikeRepository;
	
	private static final int viewCount = 5;	//한번에 보여줄 페이지 수

	@Transactional
	public Review saveReview(PrincipalDetails principal, CreateReviewRequest dto) {
		User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
				UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		Food findFood = foodRepository.findById(dto.getFoodId()).orElseThrow(() -> new NoFoodDetailException(
				FoodExceptionMessage.NO_FOOD_DETAIL_EXCEPTION_MESSAGE));

		Review savedReview = reviewRepository.save(Review.builder()
				.user(findUser)
				.food(findFood)
				.reviewRating(dto.getReviewRating())
				.reviewDescription(dto.getReviewDescription())
				.state(ReviewStateType.NORMAL)
				.build());
		
		if(findFood.getReviewsummary() == null) 
			reviewSummaryRepository.save(ReviewSummary.builder().food(findFood).reviewRating(dto.getReviewRating()).build());	
		else
			findFood.getReviewsummary().createReviewSummary(dto.getReviewRating());
		
		return savedReview;
	}

	@Transactional
	public List<ReadReviewResponse> findReview(Long foodId, Integer pageNum) {
		Pageable limitFive;
		
		if (!pageNum.equals(null)) {
			limitFive = PageRequest.of(pageNum - 1, viewCount, Sort.by("reviewCreatedDate").descending());
		} else
			throw new NotFoundPageException(ReviewExceptionMessage.NOT_FOUND_PAGE_EXCEPTION_MESSAGE);
		List<Review> reviewList = reviewRepository.findbyFoodId(foodId, limitFive);
		
		List<ReadReviewResponse> showReviewList = new ArrayList<>();
		for(Review r : reviewList) {
			int count = reviewLikeRepository.findReviewLikeCountByReview(r.getId());
			if(!r.getState().equals(ReviewStateType.DELETED)) {
			showReviewList.add(ReadReviewResponse.builder()
				.reviewId(r.getId())
				.userName(r.getUser().getName())
				.foodId(foodId)
				.reviewRating(r.getReviewRating())
				.reviewDescription(r.getReviewDescription())
				.reviewCreatedDate(r.getReviewCreatedDate())
				.reviewModifiedDate(r.getReviewModifiedDate())
				.state(r.getState())
				.userCheck(false)
				.likeCount(count)
				.build());
			}
		}
		return showReviewList;
	}

	@Transactional
	public List<ReadReviewResponse> findReviewByUserIdANDFoodID(PrincipalDetails principal, Long foodId, Integer pageNum) {
		int viewCount = 5;	//한번에 보여줄 페이지 수
		Pageable limitFive;
			
		User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
				UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		
		if (!pageNum.equals(null)) {
			limitFive = PageRequest.of(pageNum - 1, viewCount, Sort.by("reviewCreatedDate").descending());
		} else
			throw new NotFoundPageException(ReviewExceptionMessage.NOT_FOUND_PAGE_EXCEPTION_MESSAGE);
		List<Review> reviewList = reviewRepository.findbyFoodId(foodId, limitFive);
		
		List<ReadReviewResponse> showReviewList = new ArrayList<>();
		
		boolean checkedLike;
		boolean checkedUser;
		
		for (Review r : reviewList) {
			int count = reviewLikeRepository.findReviewLikeCountByReview(r.getId());
			ReviewLike findReviewLike = reviewLikeRepository.checkLikeByReview(r.getId(), findUser.getId());
			checkedUser = findUser.getId().equals(r.getUser().getId())?true:false;
			checkedLike = findReviewLike == null?false:true;
			if(!r.getState().equals(ReviewStateType.DELETED)) {				
			showReviewList.add(ReadReviewResponse.builder()
							.reviewId(r.getId())
							.userName(r.getUser().getName())
							.foodId(foodId)
							.reviewRating(r.getReviewRating())
							.reviewDescription(r.getReviewDescription())
							.reviewCreatedDate(r.getReviewCreatedDate())
							.reviewModifiedDate(r.getReviewModifiedDate())
							.state(r.getState())
							.userCheck(checkedUser)
							.userLikeCheck(checkedLike)
							.likeCount(count)
							.build());
			}
		}
		return showReviewList;
	}
	
	@Transactional
	public List<ReadReviewResponse> findReviewByUserId(PrincipalDetails principal) {
		User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
				UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		
		List<Review> reviewList = findUser.getReviewList();
		
		List<ReadReviewResponse> showReviewList = new ArrayList<>();
		
		boolean checkedLike;
		boolean checkedUser;
		
		for(Review r : reviewList) {
			int count = reviewLikeRepository.findReviewLikeCountByReview(r.getId());
			ReviewLike findReviewLike = reviewLikeRepository.checkLikeByReview(r.getId(), findUser.getId());
			checkedUser = findUser.getId().equals(r.getUser().getId())?true:false;
			checkedLike = findReviewLike == null?false:true;
			if(!r.getState().equals(ReviewStateType.DELETED)) {
			showReviewList.add(ReadReviewResponse.builder()
				.reviewId(r.getId())
				.userName(r.getUser().getName())
				.foodId(r.getFood().getId())
				.reviewRating(r.getReviewRating())
				.reviewDescription(r.getReviewDescription())
				.reviewCreatedDate(r.getReviewCreatedDate())
				.reviewModifiedDate(r.getReviewModifiedDate())
				.state(r.getState())
				.userCheck(checkedUser)
				.userLikeCheck(checkedLike)
				.likeCount(count)
				.build());
			}
		}
		Collections.sort(showReviewList, new DateComparatorASC());
		return showReviewList;
	}
	@Transactional
	public Map<String, Integer> findReviewTotalCount(Long foodId) {
		int findReviewCount = reviewRepository.findReviewTotalCount(foodId);
		int findReviewPageCount = (findReviewCount % viewCount) == 0? (findReviewCount / viewCount): (findReviewCount / viewCount) + 1;
		
		Map<String, Integer> returnMap = new HashMap<>(); 
		returnMap.put("findReviewCount", findReviewCount);
		returnMap.put("findReviewPageCount", findReviewPageCount);
		return returnMap;
	}
	
	@Transactional
	public Review changeReview(PrincipalDetails principal, UpdateReviewRequest dto) {
		Review findReview = reviewRepository.findById(dto.getReviewId()).orElseThrow(() -> new NotSuchReviewException(
				ReviewExceptionMessage.NOT_SUCH_REVIEW_EXCEPTION_MESSAGE));
		if (!findReview.getState().equals(ReviewStateType.DELETED)) {
			findReview.getFood().getReviewsummary().updateReviewSummary(findReview.getReviewRating(),
					dto.getReviewRating());
			findReview.reviewUpdate(dto.getReviewRating(), dto.getReviewDescription());
		} else
			throw new DeletedReviewException(ReviewExceptionMessage.DELETED_REVIEW_EXCEPTION_MESSAGE);
		
		return findReview;
	}
	
	@Override
	public ReviewLike changeReviewLike(PrincipalDetails principal, UpdateReviewLikeRequest dto) {
		User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
				UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		
		Review findReview = reviewRepository.findById(dto.getReviewId()).orElseThrow(() -> new NotSuchReviewException(
				ReviewExceptionMessage.NOT_SUCH_REVIEW_EXCEPTION_MESSAGE));
		
		ReviewLike findReviewLike = reviewLikeRepository.findByUserId(findReview.getId(), findUser.getId());
		
		if (!dto.isLikeCheck()) {
			if (findReviewLike == null) 
				return reviewLikeRepository.save(ReviewLike.builder().userId(findUser.getId()).review(findReview).build());
			else 
				throw new InvalidRequestReviewLikeException(ReviewExceptionMessage.INVALID_REQUEST_REVIEWLIKE_EXCEPTION_MESSAGE);			
		} else {
			if (findReviewLike == null) 
				throw new InvalidRequestReviewLikeException(ReviewExceptionMessage.INVALID_REQUEST_REVIEWLIKE_EXCEPTION_MESSAGE);
			else {
				reviewLikeRepository.delete(findReviewLike);
				return findReviewLike;
			}
		}
		
		
	}
	
	@Transactional
	public Review removeReview(PrincipalDetails principal, DeleteReviewRequest dto) {
		Review findReview = reviewRepository.findById(dto.getReviewId()).orElseThrow(() -> new NotSuchReviewException(
				ReviewExceptionMessage.NOT_SUCH_REVIEW_EXCEPTION_MESSAGE));
		
		if (!findReview.getState().equals(ReviewStateType.DELETED)) {
			findReview.getFood().getReviewsummary().deleteReviewSummary(findReview.getReviewRating());
			findReview.reviewStateUpdate(ReviewStateType.DELETED);
		} else
			throw new DeletedReviewException(ReviewExceptionMessage.DELETED_REVIEW_EXCEPTION_MESSAGE);
		return findReview;
	}
	@Transactional
	public List<ReviewRankingResponse> rankedReview() {
		Pageable limitTen = PageRequest.of(0, 10, Sort.by("avgRating").descending());
		List<ReviewSummary> reviewSummaryList = reviewSummaryRepository.findAll(limitTen).getContent();
		List<ReviewRankingResponse> rankingList = new ArrayList<>();
		for(ReviewSummary r : reviewSummaryList) {
			Food findFood = foodRepository.findById(r.getFood().getId()).orElseThrow(() -> new NoFoodDetailException(
					FoodExceptionMessage.NO_FOOD_DETAIL_EXCEPTION_MESSAGE));
			rankingList.add(ReviewRankingResponse.builder()
					.foodId(r.getFood().getId())
					.foodName(r.getFood().getFoodName())
					.avgRating(r.getAvgRating())
					.build());
		}
		return rankingList;
	}

	
	public void loginCheck(PrincipalDetails principal) {
		if (principal == null) {
			throw new NotLoginException(UserExceptionMessage.NOT_LOGIN_EXCEPTION_MESSAGE);
		}
	}

	



	
}
