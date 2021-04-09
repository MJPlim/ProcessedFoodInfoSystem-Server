package com.plim.plimserver.domain.allergy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Table(name = "user_allergy")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAllergy {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_allergy_id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;				//아직 안넣음
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_allergy_id")
	private FoodAllergy foodAllergy;
	
	@Builder
	public UserAllergy(Long userId, FoodAllergy foodAllergy) {
		this.userId = userId;
		this.foodAllergy = foodAllergy;
		this.foodAllergy.getUserAllergyList().add(this);
	}
}
