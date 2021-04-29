package com.plim.plimserver.domain.allergy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cross_allergy")
@Entity
public class CrossAllergy {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cross_allergy_id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_allergy_id1")
	private FoodAllergy foodAllergy1;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_allergy_id2")
	private FoodAllergy foodAllergy2;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "percentage", nullable = false)
	private CrossAllergyPercent percentage;						//보통 높음 낮음으로 할거같은데 enum으로 할수도?
	
	@Builder
	public CrossAllergy(FoodAllergy foodAllergy1, FoodAllergy foodAllergy2, CrossAllergyPercent percentage) {
		this.foodAllergy1 = foodAllergy1;
		this.foodAllergy2 = foodAllergy2;
		this.percentage = percentage;
		this.foodAllergy1.getCrossAllergyList1().add(this);
	}
}
