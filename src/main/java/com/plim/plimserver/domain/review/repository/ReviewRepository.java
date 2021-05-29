package com.plim.plimserver.domain.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.user.domain.User;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value = "select r from Review r join fetch r.food where r.food.id=:foodId and r.state = 'NORMAL'")
	List<Review> findByFoodId(@Param("foodId") Long foodId, Pageable pageable);
	
	@Query(value = "select r from Review r join fetch r.food join fetch r.user where r.food.id=:foodId and r.user.id=:userId and r.state = 'NORMAL'")		
	List<Review> findByFoodIdAndUserId(@Param("foodId") Long foodId, @Param("userId") Long userId, Pageable pageable);
	
	@Query(value = "select count(r) from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	int findReviewTotalCount(@Param("foodId") Long foodId);

	@Query(value = "select r from Review r join fetch r.food as rf join fetch rf.reviewsummary where r.id=:reviewId and r.state='NORMAL'")
	Optional<Review> findById(@Param("reviewId")Long reviewId);
	
	@Query(value = "select r from Review r join fetch r.food as rf join fetch r.user join fetch rf.reviewsummary where r.user.id=:userId and r.state = 'NORMAL'")
	List<Review> findByUserId(@Param("userId")Long userId);

	@Query(value = "select r from Review r join fetch r.food as rf join fetch r.user join fetch rf.reviewsummary where r.user.id=:userId and r.state = 'NORMAL'")
	List<Review> findByUserId(@Param("userId")Long userId, Pageable pagable);
	
	@Query(value = "select * from review where food_id=:foodId and user_id=:userId and review_state='NORMAL' limit 1", nativeQuery = true)
	Review existsByFoodIdAndUserId(@Param("foodId") Long foodId, @Param("userId") Long userId);

	@Query(value = "select count(r) from Review r where r.user=:user and r.state = 'NORMAL'")
	Long countAllByUser(@Param("user")User user);

}
