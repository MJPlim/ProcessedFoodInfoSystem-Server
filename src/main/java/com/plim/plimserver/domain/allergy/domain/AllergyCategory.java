package com.plim.plimserver.domain.allergy.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "allergy_category")
@Entity
public class AllergyCategory {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "allergy_category_id")
	private Integer id;
	
	@Column(name = "allergy_name", unique=true)
	private String allergyName;
	
	@OneToMany(mappedBy = "allergyCategory", cascade = CascadeType.ALL, orphanRemoval = true)	//사실상 지워질일은 없음
	private List<FoodAllergy> foodAllergyList = new ArrayList<>();
	
	@Builder
	public AllergyCategory(String allergyName) {
		this.allergyName = allergyName;
	}
}
