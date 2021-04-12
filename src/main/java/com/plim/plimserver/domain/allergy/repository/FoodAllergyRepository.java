package com.plim.plimserver.domain.allergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.allergy.domain.FoodAllergy;

public interface FoodAllergyRepository extends JpaRepository<FoodAllergy, Long> {

}
