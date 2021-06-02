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

import java.util.Arrays;
import java.util.List;

import static com.plim.plimserver.domain.food.domain.QFood.food;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Food> search(String sortElement, String category, String foodName, String manufacturerName
            , List<String> allergyList, String order, Pageable pageable) {
        QueryResults<Food> result = queryFactory.selectFrom(food)
                                                .where(eqFoodName(foodName), eqManufacturerName(manufacturerName)
                                                        , eqAllergies(allergyList), eqCategory(category))
                                                .orderBy(orderType(sortElement, order))
                                                .offset(pageable.getOffset())
                                                .limit(pageable.getPageSize())
                                                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<Food> findByWideCategory(String[] categories, Pageable pageable) {
        QueryResults<Food> result = queryFactory.selectFrom(food)
                                                .where(eqCategoriesForWideCategory(categories))
                                                .offset(pageable.getOffset())
                                                .limit(pageable.getPageSize())
                                                .fetchResults();
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression eqCategory(String category) {
        return category != null ? food.category.contains(category) : null;
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

    private BooleanExpression eqCategoriesForWideCategory(String[] categories) {
        return categories != null ? Expressions.anyOf(Arrays.stream(categories).map(this::isFilteredCategoryForWideCategory)
                                                            .toArray(BooleanExpression[]::new)) : null;
    }

    private BooleanExpression isFilteredCategoryForWideCategory(String category) {
        return food.category.contains(category);
    }

    private OrderSpecifier<?>[] orderType(String sortElement, String order) {
        if (sortElement == null) {
            return order.equals("asc") ? new OrderSpecifier[]{food.foodName.asc()}
                    : new OrderSpecifier[]{food.foodName.desc()};
        }else if (sortElement.equals(SortElement.RANK.getMessage())) {
            return order.equals("asc") ? new OrderSpecifier[]{food.reviewsummary.avgRating.asc(), food.reviewsummary.reviewCount.asc()}
                    : new OrderSpecifier[]{food.reviewsummary.avgRating.desc(), food.reviewsummary.reviewCount.desc()};
        } else if (sortElement.equals(SortElement.REVIEW_COUNT.getMessage())) {
            return order.equals("asc") ? new OrderSpecifier[]{food.reviewsummary.reviewCount.asc()}
                    : new OrderSpecifier[]{food.reviewsummary.reviewCount.desc()};
        } else if (sortElement.equals(SortElement.MANUFACTURER.getMessage())) {
            return order.equals("asc") ? new OrderSpecifier[]{food.manufacturerName.asc()}
                    : new OrderSpecifier[]{food.manufacturerName.desc()};
        }else {
            return null;
        }
    }
}
