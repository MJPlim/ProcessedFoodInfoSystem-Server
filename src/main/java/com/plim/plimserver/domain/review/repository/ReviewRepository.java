package com.plim.plimserver.domain.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
//	public List<Review> findAllByIdOrderByreviewCreatedDateDesc(Long id);
}
