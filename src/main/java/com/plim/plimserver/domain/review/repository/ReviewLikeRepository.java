package com.plim.plimserver.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.review.domain.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long>{

}