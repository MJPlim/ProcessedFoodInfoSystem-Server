package com.plim.plimserver.domain.review.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.exception.OutCountInputReviewException;
import com.plim.plimserver.domain.review.exception.ReviewExceptionMessage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "review_summary")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSummary {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_summary_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")
	private Food food;
	
	@Column(name = "review_count")
	@ColumnDefault("0")
	private int reviewCount;

	@Column(name = "one_count")
	@ColumnDefault("0")
	private int oneCount;
	
	@Column(name = "two_count")
	@ColumnDefault("0")
	private int twoCount;
	
	@Column(name = "three_count")
	@ColumnDefault("0")
	private int threeCount;
	
	@Column(name = "four_count")
	@ColumnDefault("0")
	private int fourCount;
	
	@Column(name = "five_count")
	@ColumnDefault("0")
	private int fiveCount;
	
	@Column(name = "sum_rating")
	@ColumnDefault("0")
	private long sumRating;
	
	@Column(name = "avg_rating")
	@ColumnDefault("0.00")
	private float avgRating;
	
	@Builder
	public ReviewSummary(Food food, int reviewRating) {
		this.food = food;
		createReviewSummary(reviewRating);
	}
	
	public void createReviewSummary(int reviewRating) {
		switch (reviewRating) {
		case 1: this.oneCount++; break;
		case 2: this.twoCount++; break;
		case 3:	this.threeCount++; break;
		case 4: this.fourCount++; break;
		case 5: this.fiveCount++; break;
		default: throw new OutCountInputReviewException(ReviewExceptionMessage.OUT_COUNT_INPUT_REVIEW_EXCEPTION_MESSAGE);
		}
		this.reviewCount++;
		this.sumRating += reviewRating;
		this.avgRating = (float)sumRating / (float)reviewCount;
	}
	
	public void updateReviewSummary(int oldRevuewRating, int newReviewRating) {
		switch (oldRevuewRating) {
		case 1: this.oneCount--; break;
		case 2: this.twoCount--; break;
		case 3:	this.threeCount--; break;
		case 4: this.fourCount--; break;
		case 5: this.fiveCount--; break;
		default: throw new OutCountInputReviewException(ReviewExceptionMessage.OUT_COUNT_INPUT_REVIEW_EXCEPTION_MESSAGE);
		}
		this.reviewCount--;
		this.sumRating -= oldRevuewRating;
		
		switch (newReviewRating) {
		case 1: this.oneCount++; break;
		case 2: this.twoCount++; break;
		case 3:	this.threeCount++; break;
		case 4: this.fourCount++; break;
		case 5: this.fiveCount++; break;
		default: throw new OutCountInputReviewException(ReviewExceptionMessage.OUT_COUNT_INPUT_REVIEW_EXCEPTION_MESSAGE);
		}
		this.reviewCount++;
		this.sumRating += newReviewRating;
		this.avgRating = (float)sumRating / (float)reviewCount;
	}
	
	public void deleteReviewSummary(int reviewRating) {
		switch (reviewRating) {
		case 1: this.oneCount--; break;
		case 2: this.twoCount--; break;
		case 3:	this.threeCount--; break;
		case 4: this.fourCount--; break;
		case 5: this.fiveCount--; break;
		default: throw new OutCountInputReviewException(ReviewExceptionMessage.OUT_COUNT_INPUT_REVIEW_EXCEPTION_MESSAGE);
		}
		this.reviewCount--;
		this.sumRating -= reviewRating;
		this.avgRating = (float)sumRating / (float)reviewCount;
	}
	
	public void insertFood(Food food) {
		this.food = food;
	}
}
