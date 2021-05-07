package com.plim.plimserver.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plim.plimserver.domain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value = "select r from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	public List<Review> findbyFoodId(@Param("foodId") Long foodId, Pageable pagable);
	
	@Query(value = "select r from Review r where r.food.id=:foodId and r.user.id=:userId and r.state = 'NORMAL'")
	public List<Review> findbyFoodIdAndUserId(@Param("foodId") Long foodId, @Param("userId") Long userId, Pageable pagable);
	
	@Query(value = "select count(r) from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	public int findReviewTotalCount(@Param("foodId") Long foodId);
}
