package com.plim.plimserver.domain.food.util;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.domain.Review;

import java.util.Comparator;

public class SortByReviewRateAndDesc implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        int o1Sum = o1.getReviewList().stream().mapToInt(Review::getReviewRating).sum();
        int o2Sum = o2.getReviewList().stream().mapToInt(Review::getReviewRating).sum();
        float o1Rate = o1Sum / (float)o1.getReviewList().size();
        float o2Rate = o2Sum / (float)o2.getReviewList().size();

        if (o1Rate > o2Rate) {
            return -1;
        } else if (o1Rate < o2Rate) {
            return 1;
        }
        return 0;
    }
}
