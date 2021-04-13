package com.plim.plimserver.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
}
