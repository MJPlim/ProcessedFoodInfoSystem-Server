package com.plim.plimserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.plim.plimserver.domain.review.domain.ReviewLike;
import com.plim.plimserver.domain.review.dto.ReadReviewResponse;
import com.plim.plimserver.domain.review.repository.ReviewLikeRepository;
import com.plim.plimserver.domain.review.repository.ReviewRepository;
import com.plim.plimserver.domain.review.service.ReviewService;
import com.plim.plimserver.global.config.DatabaseConfig;

@SpringBootTest
@Import(DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReviewTest {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReviewLikeRepository reviewLikeRepository;
	
	@Autowired
	private ReviewService reviewService;
	
//	@Test
//	public void timeTest() {
//		long start = System.currentTimeMillis();
//        System.out.println("@@@ getOgExByMemberId 시작");
//
//        List<ReadReviewResponse> list = reviewService.findReview(12529L, 1);
//
//        long end = System.currentTimeMillis();
//        System.out.println("@@@ getOgExByMemberId 완료 실행 시간 : " + (end - start) / 1000.0);
//        
//        System.out.println(list.size());
//	}
	
	
	
	
	@Test
	public void pageTest() {
		Pageable page = PageRequest.of(2, 5, Sort.by("reviewCreatedDate").descending());
		reviewRepository.findByFoodIdAndUserId(12529L, 238L, page).forEach(review -> System.out.println(review.getId() + " " +review.getReviewDescription()));
	}
	
	@Test
	public void countTest() {
		int count = reviewRepository.findReviewTotalCount(12529L);
		System.out.println(count);
	}
	
	@Test
	public void likeTest() {
		ReviewLike reviewLike = reviewLikeRepository.findByUserId(72L, 238L);
		System.out.println(reviewLike.getReview().getReviewDescription());
	}
}
