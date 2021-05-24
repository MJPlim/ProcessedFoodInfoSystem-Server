package com.plim.plimserver.domain.food.repository;

import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.domain.SortElement;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.plim.plimserver.domain.food.domain.QFood.food;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Food> search(String sortElement, String foodName, String manufacturerName, List<String> allergyList, Pageable pageable) {
        QueryResults<Food> result = queryFactory.selectFrom(food)
                                                          .where(eqFoodName(foodName), eqManufacturerName(manufacturerName), eqAllergies(allergyList))
                                                          .orderBy(orderType(sortElement))
                                                          .offset(pageable.getOffset())
                                                          .limit(pageable.getPageSize())
                                                          .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression eqFoodName(String foodName) {
        return foodName != null ? food.foodName.contains(foodName) : null;
    }

    private BooleanExpression eqManufacturerName(String manufacturerName) {
        return manufacturerName != null ? food.manufacturerName.contains(manufacturerName) : null;
    }

    private BooleanExpression eqAllergies(List<String> allergyList) {
        return allergyList != null ? Expressions.allOf(allergyList.stream().map(this::isFilteredAllergy).toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isFilteredAllergy(String allergy) {
        return food.allergyMaterials.contains(allergy).not();
    }

    private OrderSpecifier<?>[] orderType(String sortElement) {
        if (sortElement.equals(SortElement.RANK.getMessage())) {
            return new OrderSpecifier[]{food.reviewsummary.avgRating.desc(), food.reviewsummary.reviewCount.desc()};
        } else if (sortElement.equals(SortElement.REVIEW_COUNT.getMessage())) {
            return new OrderSpecifier[]{food.reviewsummary.reviewCount.desc()};
        } else if (sortElement.equals(SortElement.MANUFACTURER.getMessage())) {
            return new OrderSpecifier[]{food.manufacturerName.asc()};
        } else {
            return new OrderSpecifier[]{food.foodName.asc()};
        }
    }
}
