package com.plim.plimserver.domain.review.service;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.exception.FoodExceptionMessage;
import com.plim.plimserver.domain.food.exception.NoFoodDetailException;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.domain.ReviewLike;
import com.plim.plimserver.domain.review.domain.ReviewStateType;
import com.plim.plimserver.domain.review.domain.ReviewSummary;
import com.plim.plimserver.domain.review.dto.*;
import com.plim.plimserver.domain.review.exception.*;
import com.plim.plimserver.domain.review.repository.ReviewLikeRepository;
import com.plim.plimserver.domain.review.repository.ReviewRepository;
import com.plim.plimserver.domain.review.repository.ReviewSummaryRepository;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    private static final int viewCount = 5;    //한번에 보여줄 페이지 수

    @Transactional
    public void saveReview(PrincipalDetails principal, CreateReviewRequest dto) {
        User user = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
                UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        Food food = foodRepository.findById(dto.getFoodId()).orElseThrow(() -> new NoFoodDetailException(
                FoodExceptionMessage.NO_FOOD_DETAIL_EXCEPTION_MESSAGE));
        Review duplicatedCheck = reviewRepository.existsByFoodIdAndUserId(food.getId(), user.getId());
        if (duplicatedCheck != null)
            throw new AlreadyWrittenReivewException(ReviewExceptionMessage.ALREADY_WRITTEN_REVIEW_EXCEPTION_MESSAGE);
        food.addReview(reviewRepository.save(Review.of(user, food, dto)));

        if (food.getReviewsummary() == null) reviewSummaryRepository.save(ReviewSummary.of(food, dto));
        else food.getReviewsummary().createReviewSummary(dto.getReviewRating());
    }

    private List<ReadReviewResponse> getReadReviewResponseList(List<Review> list, Long foodId, User user) {
        return list.stream()
                .filter(review -> !review.getState().equals(ReviewStateType.DELETED))
                .map(review -> {
                    int count = review.reviewLikeCount();
                    if (user == null) return ReadReviewResponse.of(review, foodId, review.getFood().getFoodName(), false, false, count);
                    else {

                        ReviewLike findReviewLike = review.checkLike(review.getId(), user.getId());
                        if (foodId == 0) return ReadReviewResponse.of(review, review.getFood().getId(), review.getFood().getFoodName(),
                                user.getId().equals(review.getUser().getId()), findReviewLike == null?false:true, count);
                        else return ReadReviewResponse.of(review, foodId, review.getFood().getFoodName(),
                                user.getId().equals(review.getUser().getId()), findReviewLike == null?false:true, count);
                    }
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReadReviewResponse> findReview(Long foodId, Integer pageNum) {
        Pageable limitFive;
        if (pageNum != null)
            limitFive = PageRequest.of(pageNum - 1, viewCount, Sort.by("reviewCreatedDate").descending());
        else throw new NotFoundPageException(ReviewExceptionMessage.NOT_FOUND_PAGE_EXCEPTION_MESSAGE);
        List<Review> reviewList = reviewRepository.findByFoodId(foodId, limitFive);

        return getReadReviewResponseList(reviewList, foodId, null);
    }

    @Transactional(readOnly = true)
    public List<ReadReviewResponse> findReviewByUserIdANDFoodID(PrincipalDetails principal, Long foodId, Integer pageNum) {
        Pageable limitFive;
        User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
                UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        if (pageNum != null)
            limitFive = PageRequest.of(pageNum - 1, viewCount, Sort.by("reviewCreatedDate").descending());
        else throw new NotFoundPageException(ReviewExceptionMessage.NOT_FOUND_PAGE_EXCEPTION_MESSAGE);
        List<Review> reviewList = reviewRepository.findByFoodId(foodId, limitFive);

        return getReadReviewResponseList(reviewList, foodId, findUser);
    }

    @Transactional(readOnly = true)
    public List<ReadReviewResponse> findReviewByUserId(PrincipalDetails principal, Integer pageNum) {
    	Pageable limitTen;
    	List<Review> reviewList;
    	User user = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
                UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        if (pageNum != null) {
        	limitTen = PageRequest.of(pageNum - 1, 10, Sort.by("reviewModifiedDate").ascending());        	
        	reviewList = reviewRepository.findByUserId(user.getId(), limitTen);
        }else
        	reviewList = reviewRepository.findByUserId(user.getId());
        return getReadReviewResponseList(reviewList, 0L, user).stream()
                .sorted(Comparator.comparing(ReadReviewResponse::getReviewCreatedDate))
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public int findUserReviewCount(PrincipalDetails principal) {
    	User user = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
                UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
    	return reviewRepository.findUserReviewCount(user.getId());
    }

    @Transactional(readOnly = true)
   	public ReadSummaryResponse findReviewSummary(Long foodId) {
    	int findReviewCount = reviewRepository.findReviewTotalCount(foodId);
        int findReviewPageCount = (findReviewCount % viewCount) == 0 ? (findReviewCount / viewCount) : (findReviewCount / viewCount) + 1;
        ReviewSummary reviewSummary = reviewSummaryRepository.findByFoodId(foodId);
        if(reviewSummary == null) 
        	return ReadSummaryResponse.defaultSummary(foodId);
        else
       		return ReadSummaryResponse.of(reviewSummary, findReviewCount, findReviewPageCount);
    }
    
	@Transactional(readOnly = true)
	public ReadReviewIdResponse findReviewByReviewId(PrincipalDetails principal, Long reviewId) {
		User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
	                UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
		Review findReview = reviewRepository.findById(reviewId).orElseThrow(() -> new NotSuchReviewException(
                ReviewExceptionMessage.NOT_SUCH_REVIEW_EXCEPTION_MESSAGE));
		
		if(findReview.getUser().getId() == findUser.getId()) {
			return ReadReviewIdResponse.of(findReview);
		}else
			 throw new NotApproachReviewException(ReviewExceptionMessage.NOT_APPROACH_REVIEW_EXCEPTION_MESSAGE);
	}

    @Transactional
    public Review changeReview(PrincipalDetails principal, UpdateReviewRequest dto) {
        User findUser = userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException(
                UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        Review findReview = reviewRepository.findById(dto.getReviewId()).orElseThrow(() -> new NotSuchReviewException(
                ReviewExceptionMessage.NOT_SUCH_REVIEW_EXCEPTION_MESSAGE));
        if (!findReview.getState().equals(ReviewStateType.DELETED)) {
            if (findReview.getUser().getId().equals(findUser.getId())) {
                findReview.getFood().getReviewsummary().updateReviewSummary(findReview.getReviewRating(),
                        dto.getReviewRating());
                findReview.reviewUpdate(dto.getReviewRating(), dto.getReviewDescription());
            } else
                throw new NotApproachReviewException(ReviewExceptionMessage.NOT_APPROACH_REVIEW_EXCEPTION_MESSAGE);
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

    @Transactional(readOnly = true)
    public List<ReviewRankingResponse> rankedReview() {
        Pageable limitTen = PageRequest.of(0, 10, Sort.by("avgRating").descending());
        return reviewSummaryRepository.findReviewRankingTen(limitTen).stream()
                .map(ReviewRankingResponse::from)
                .collect(Collectors.toList());
    }



   

}
