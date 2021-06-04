package com.plim.plimserver.domain.food.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum FoodCategory {

    SNACK("간식", Arrays.asList("과자", "떡", "빵", "사탕/껌/젤리", "아이스크림", "초콜릿")),
    DRINK("음료/차", Arrays.asList("음료", "커피", "커피/차")),
    DAIRY("유제품", Arrays.asList("유제품")),
    MEAT("육류", Arrays.asList("육류", "햄/소시지")),
    AGRICULTURAL_FISHERY("농수산물", Arrays.asList("계란", "과일/채소", "김", "수산물", "견과", "곡류")),
    KIMCHI("김치", Arrays.asList("김치", "젓갈")),
    SEASONING("조미료", Arrays.asList("설탕", "소금", "소스", "장류")),
    INSTANT("즉석조리식품", Arrays.asList("즉석조리식품")),
    MATERIAL("식재료", Arrays.asList("국수", "두부", "식용유", "어묵")),
    ETC("기타", Arrays.asList("기타가공품")),
    EMPTY("없음", Collections.EMPTY_LIST);

    private final String message;
    private final List<String> categoryList;

    public static List<String> getCategoryList(String category) {
        return Arrays.stream(FoodCategory.values())
                .filter(c -> c.getMessage().equals(category))
                .findAny()
                .orElse(EMPTY)
                .getCategoryList();
    }

}
