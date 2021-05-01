package com.plim.plimserver;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.plim.plimserver.domain.allergy.domain.AllergyCategory;
import com.plim.plimserver.domain.allergy.domain.FoodAllergy;
import com.plim.plimserver.domain.allergy.domain.UserAllergy;
import com.plim.plimserver.domain.allergy.repository.AllergyCategoryRepository;
import com.plim.plimserver.domain.allergy.repository.FoodAllergyRepository;
import com.plim.plimserver.domain.allergy.repository.UserAllergyRepository;
import com.plim.plimserver.global.config.DatabaseConfig;

@DataJpaTest
@Import(DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AllergyTest {
	@Autowired
	private EntityManager em;

	@Autowired
	private AllergyCategoryRepository allergyCategoryRepository;
	
	@Autowired
	private FoodAllergyRepository foodAllergyRepository;
	
	@Autowired
	private UserAllergyRepository userAllergyRepository;
	
	@Test
	@Rollback(false)
	public void AllergyCreateTest() {
		AllergyCategory allergyCategory = AllergyCategory.builder().allergyName("갑각류").build();
		allergyCategoryRepository.save(allergyCategory);
		
		FoodAllergy foodAllergy = FoodAllergy.builder().allergyCategory(allergyCategory).allergyMaterial("새우").build();
		foodAllergyRepository.save(foodAllergy);
		
//		Optional<FoodAllergy> foodAllergy = foodAllergyRepository.findById(90);
//		System.out.println(foodAllergy.get().getAllergyMaterial());
			
		UserAllergy userAllergy = UserAllergy.builder().userId(225L).foodAllergy(foodAllergy).build();
		userAllergyRepository.save(userAllergy);
	}
}
