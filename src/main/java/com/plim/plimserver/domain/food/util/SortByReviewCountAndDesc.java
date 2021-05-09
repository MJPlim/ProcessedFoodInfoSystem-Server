package com.plim.plimserver.domain.food.util;

import com.plim.plimserver.domain.food.domain.Food;

import java.util.Comparator;

public class SortByReviewCountAndDesc implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        if (o1.getReviewList().size() > o2.getReviewList().size()) {
            return -1;
        } else if (o1.getReviewList().size() < o2.getReviewList().size()) {
            return 1;
        }
        return 0;
    }
}
