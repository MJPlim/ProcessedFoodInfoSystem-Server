package com.plim.plimserver.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plim.plimserver.domain.review.domain.ReviewSummary;

public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, Long>{
	
	@Query(value = "Select rs from ReviewSummary rs Where rs.food.id = :foodId")
	public ReviewSummary findByFoodId(@Param("foodId") Long foodId);
	
	@Query(value = "select rs from ReviewSummary rs join fetch rs.food")
	public List<ReviewSummary> findReviewRankingTen(Pageable pageable);
}
