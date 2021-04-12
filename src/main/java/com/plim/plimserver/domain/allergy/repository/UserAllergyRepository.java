package com.plim.plimserver.domain.allergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.allergy.domain.UserAllergy;

public interface UserAllergyRepository extends JpaRepository<UserAllergy, Long>{

}
