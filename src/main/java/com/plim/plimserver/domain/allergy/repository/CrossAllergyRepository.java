package com.plim.plimserver.domain.allergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.allergy.domain.CrossAllergy;

public interface CrossAllergyRepository extends JpaRepository<CrossAllergy, Long>{

}
