package com.plim.plimserver.domain.food.util;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.review.domain.ReviewStateType;

import java.util.Comparator;

public class SortByReviewCountAndDesc implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        int a = (int)o1.getReviewList().stream().filter(o -> o.getState().equals(ReviewStateType.NORMAL)).count();
        int b = (int)o2.getReviewList().stream().filter(o -> o.getState().equals(ReviewStateType.NORMAL)).count();
        if (a > b) {
            return -1;
        } else if (a < b) {
            return 1;
        }
        return 0;
    }
}
