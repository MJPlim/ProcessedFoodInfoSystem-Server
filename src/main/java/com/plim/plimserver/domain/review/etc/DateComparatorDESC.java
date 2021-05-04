package com.plim.plimserver.domain.review.etc;

import java.sql.Timestamp;
import java.util.Comparator;

import com.plim.plimserver.domain.review.dto.ReadReviewResponse;

public class DateComparatorDESC implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		Timestamp time1 = ((ReadReviewResponse)o1).getReviewCreatedDate();
		Timestamp time2 = ((ReadReviewResponse)o2).getReviewCreatedDate();
		return time1.compareTo(time2);
	}

}

