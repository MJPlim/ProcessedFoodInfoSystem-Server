package com.plim.plimserver.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.user.domain.User;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value = "select r from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	List<Review> findByFoodId(@Param("foodId") Long foodId, Pageable pageable);
	
	@Query(value = "select r from Review r where r.food.id=:foodId and r.user.id=:userId and r.state = 'NORMAL'")
	List<Review> findByFoodIdAndUserId(@Param("foodId") Long foodId, @Param("userId") Long userId, Pageable pageable);
	
	@Query(value = "select count(r) from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	int findReviewTotalCount(@Param("foodId") Long foodId);

//	boolean existsByFoodAndUser(Food food, User user);
	
	@Query(value = "select * from review where food_id=12505 and user_id=238 and review_state='NORMAL' limit 1", nativeQuery = true)
	Review existsByFoodIdAndUserId(@Param("foodId") Long foodId, @Param("userId") Long userId);

	@Query(value = "select count(r) from Review r where r.user=:user and r.state = 'NORMAL'")
	Long countAllByUser(@Param("user")User user);

}
