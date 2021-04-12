package com.plim.plimserver.domain.review.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review_like")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_like_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;
	
	@Column(name = "user_id")
	private Long userId;

	@Builder
	public ReviewLike(Review review, Long userId) {
		this.review = review;
		this.userId = userId;
		this.review.getReviewLikeList().add(this);
	}
	
	
}
