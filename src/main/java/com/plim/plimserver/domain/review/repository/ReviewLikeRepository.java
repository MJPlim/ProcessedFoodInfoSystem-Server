package com.plim.plimserver.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plim.plimserver.domain.review.domain.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long>{
	public ReviewLike findByUserId(Long userId);
	
	@Query(value = "Select count(rl) from ReviewLike rl where rl.review.id =:reviewId")
	public int findReviewLikeCountByReview(@Param("reviewId")Long reviewId);
	
	@Query(value = "Select rl from ReviewLike rl where rl.review.id =:reviewId")
	public ReviewLike checkLikeByReview(@Param("reviewId")Long reviewId);
}
