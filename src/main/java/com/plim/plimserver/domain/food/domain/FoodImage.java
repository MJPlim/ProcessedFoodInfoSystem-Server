package com.plim.plimserver.domain.food.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodImage {
	@Column(name = "food_image_address")
	private String foodImageAddress;

	@Column(name = "food_mete_image_address")
	private String foodMeteImageAddress;
}
