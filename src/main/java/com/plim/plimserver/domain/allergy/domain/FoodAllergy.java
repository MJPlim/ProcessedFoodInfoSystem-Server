package com.plim.plimserver.domain.allergy.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "food_allergy")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodAllergy {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "food_allergy_id")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "allergy_category_id")
	private AllergyCategory allergyCategory;
	
	@OneToMany(mappedBy = "foodAllergy", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserAllergy> userAllergyList = new ArrayList<>();
	
	@OneToMany(mappedBy = "foodAllergy1", cascade = CascadeType.ALL, orphanRemoval = true)		//좀 애매하다?
	private List<CrossAllergy> crossAllergyList1 = new ArrayList<>();

	@OneToMany(mappedBy = "foodAllergy2")
	private List<CrossAllergy> crossAllergyList2 = new ArrayList<>();
	
	@Column(name = "allergy_material")
	private String allergyMaterial;
	
	@Builder
	public FoodAllergy(AllergyCategory allergyCategory, String allergyMaterial) {
		this.allergyCategory = allergyCategory;
		this.allergyMaterial = allergyMaterial;
		this.allergyCategory.getFoodAllergyList().add(this);
	}
	
}
