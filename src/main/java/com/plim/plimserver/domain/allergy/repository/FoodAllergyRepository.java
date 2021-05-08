package com.plim.plimserver.domain.allergy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.allergy.domain.FoodAllergy;

public interface FoodAllergyRepository extends JpaRepository<FoodAllergy, Integer> {
	public Optional<FoodAllergy> findByAllergyMaterial(String allergyMaterial);
}
