package com.plim.plimserver.domain.allergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.plim.plimserver.domain.allergy.domain.UserAllergy;

public interface UserAllergyRepository extends JpaRepository<UserAllergy, Long>{

	@Query(value = "Select ua from UserAllergy ua where ua.user.id =:userId and ua.foodAllergy.id = :foodAllergyId")
	public UserAllergy findByUserIDandFoodAllergyID(@Param("userId")Long userId, @Param("foodAllergyId")Integer foodAllergyId);

	@Transactional
	@Modifying
	@Query(value = "delete from UserAllergy ua where ua.user.id =:userId")
	public void deleteByUserID(@Param("userId")Long userId);

}
