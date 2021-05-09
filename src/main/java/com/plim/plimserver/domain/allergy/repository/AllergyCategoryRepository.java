package com.plim.plimserver.domain.allergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.allergy.domain.AllergyCategory;

public interface AllergyCategoryRepository extends JpaRepository<AllergyCategory, Integer>{
	public AllergyCategory findByAllergyName(String allergyName);
}
