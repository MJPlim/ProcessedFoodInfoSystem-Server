package com.plim.plimserver.domain.food.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

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
public class FoodDetail {
	@Column(name = "expiration_date")
	private String expriationDate;
	
	@Lob
	@Column(name = "materials")
	private String materials;
	
	@Lob
	@Column(name = "nutrient")
	private String nutrient;
	
	@Column(name = "capacity")
	private String capacity;
}
