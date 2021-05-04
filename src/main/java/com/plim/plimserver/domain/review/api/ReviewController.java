package com.plim.plimserver.domain.review.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.dto.CreateReviewRequest;
import com.plim.plimserver.domain.review.dto.CreateReviewResponse;
import com.plim.plimserver.domain.review.dto.DeleteReviewRequest;
import com.plim.plimserver.domain.review.dto.DeleteReviewResponse;
import com.plim.plimserver.domain.review.dto.ReadReviewRequest;
import com.plim.plimserver.domain.review.dto.ReadReviewResponse;
import com.plim.plimserver.domain.review.dto.ReviewRankingResponse;
import com.plim.plimserver.domain.review.dto.UpdateReviewRequest;
import com.plim.plimserver.domain.review.dto.UpdateReviewResponse;
import com.plim.plimserver.domain.review.service.ReviewService;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"Review"})
@RequiredArgsConstructor
@RestController
public class ReviewController {
	private final ReviewService reviewService;
	
	@ApiOperation(value = "리뷰 생성", notes = "로그인한 사용자가 리뷰를 작성한다.")
	@PostMapping("createReview")
	public ResponseEntity<CreateReviewResponse> createReview(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody CreateReviewRequest dto){
		Review savedReview = reviewService.saveReview(principal, dto);
		return ResponseEntity.ok(CreateReviewResponse.builder()
				.message("리뷰 저장 완료")
				.build());
	}

	@ApiOperation(value = "리뷰 불러오기", notes = "상품에 작성된 리뷰들을 불러온다.")
	@GetMapping("readReview")
	public List<ReadReviewResponse> readReview(@RequestBody ReadReviewRequest dto){
		return reviewService.findReview(dto);
	}
	
	@ApiOperation(value = "유저 리뷰들 불러오기", notes = "로그인한 사용자가 작성한 리뷰들을 마이페이지에서 볼 수 있다.")
	@GetMapping("readReviewByUserID")
	public List<ReadReviewResponse> readReviewByUserID(@AuthenticationPrincipal PrincipalDetails principal){
		return reviewService.findReviewByUserId(principal);
	}
	
	@ApiOperation(value = "리뷰 업데이트", notes = "로그인한 사용자가 작성한 리뷰를 수정한다.")
	@PostMapping("updateReview")
	public ResponseEntity<UpdateReviewResponse> updateReview(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody UpdateReviewRequest dto){
		Review reivew = reviewService.changeReview(principal, dto);
		return ResponseEntity.ok(UpdateReviewResponse.builder()
				.message("리뷰 변경 완료")
				.build());
	}
	
	@ApiOperation(value = "리뷰 삭제", notes = "로그인한 사용자가 작성한 리뷰를 삭제한다.")
	@PostMapping("deleteReview")
	public ResponseEntity<DeleteReviewResponse> deleteReview(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody DeleteReviewRequest dto){
		Review reivew = reviewService.removeReview(principal, dto);
		return ResponseEntity.ok(DeleteReviewResponse.builder()
				.message("리뷰 삭제 완료")
				.build());
	}
	
	@ApiOperation(value = "상품 리뷰 별점순 랭킹", notes = "상품에 매겨진 별점을 평균내서 순위를 매긴다.")
	@GetMapping("reviewRanking")
	public List<ReviewRankingResponse> readReviewRanking(){
		return reviewService.rankedReview();
	}
}
