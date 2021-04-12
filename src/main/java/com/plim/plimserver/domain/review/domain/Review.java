package com.plim.plimserver.domain.review.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.plim.plimserver.domain.food.domain.Food;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;
	
	@Column(name = "user_id")				//매핑 아직 안함
	private Long userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")				//이것두
	private Food food;
	
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewLike> reviewLikeList = new ArrayList<>();
	
	@Column(name = "review_rating", nullable = false)
	private float reviewRating;
	
	@Lob
	@Column(name = "review_description")
	private String reviewDescription;
	
	@CreationTimestamp
	@Column(name = "review_created_date")
	private Timestamp reviewCreatedDate;
	
	@UpdateTimestamp
	@Column(name = "review_modified_date")
	private Timestamp reviewModifiedDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "review_state", nullable = false)
	private ReviewStateType state;
	
	@Builder
	public Review(Long userId, Food food, float reviewRating, String reviewDescription, ReviewStateType state) {
		this.userId = userId;
		this.food = food;
		this.reviewRating = reviewRating;
		this.reviewDescription = reviewDescription;
		this.state = state;
		this.food.getReviewList().add(this);
	}
	
	public void reviewUpdate(float reviewRating, String reviewDescription) {
		this.reviewRating = reviewRating;
		this.reviewDescription = reviewDescription;
	}
	
	public void reviewStateUpdate(ReviewStateType state) {
		this.state = state;
	}
}
