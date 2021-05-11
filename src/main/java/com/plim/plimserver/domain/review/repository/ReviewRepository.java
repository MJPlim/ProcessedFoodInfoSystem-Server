package com.plim.plimserver.domain.review.repository;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value = "select r from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	List<Review> findByFoodId(@Param("foodId") Long foodId, Pageable pageable);
	
	@Query(value = "select r from Review r where r.food.id=:foodId and r.user.id=:userId and r.state = 'NORMAL'")
	List<Review> findByFoodIdAndUserId(@Param("foodId") Long foodId, @Param("userId") Long userId, Pageable pageable);
	
	@Query(value = "select count(r) from Review r where r.food.id=:foodId and r.state = 'NORMAL'")
	int findReviewTotalCount(@Param("foodId") Long foodId);

	boolean existsByFoodAndUser(Food food, User user);

	Long countAllByUser(User user);

}
