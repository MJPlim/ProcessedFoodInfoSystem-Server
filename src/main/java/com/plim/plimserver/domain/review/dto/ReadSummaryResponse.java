package com.plim.plimserver.domain.review.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.domain.ReviewSummary;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadSummaryResponse {

    private Long foodId;
 
    private int oneCount;

    private int twoCount;

    private int threeCount;

    private int fourCount;

    private int fiveCount;

    private long sumRating;

    private float avgRating;
    
    private int reviewCount;
    
    private int reviewPageCount;
    
    public static ReadSummaryResponse of(ReviewSummary reviewSummary, int findReviewCount, int findReviewPageCount) {
    	return ReadSummaryResponse.builder()
    			.foodId(reviewSummary.getFood().getId())
    			.oneCount(reviewSummary.getOneCount())
    			.twoCount(reviewSummary.getTwoCount())
    			.threeCount(reviewSummary.getThreeCount())
    			.fourCount(reviewSummary.getFourCount())
    			.fiveCount(reviewSummary.getFiveCount())
    			.sumRating(reviewSummary.getSumRating())
    			.avgRating(reviewSummary.getAvgRating())
    			.reviewCount(findReviewCount)
    			.reviewPageCount(findReviewPageCount)
    			.build();
    }
    
    public static ReadSummaryResponse defaultSummary(Long foodId) {
    	return ReadSummaryResponse.builder()
    			.foodId(foodId)
    			.oneCount(0)
    			.twoCount(0)
    			.threeCount(0)
    			.fourCount(0)
    			.fiveCount(0)
    			.sumRating(0)
    			.avgRating(0)
    			.reviewCount(0)
    			.reviewPageCount(0)
    			.build();
    }
    
}
