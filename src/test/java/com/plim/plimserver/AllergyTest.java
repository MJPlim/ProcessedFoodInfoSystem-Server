package com.plim.plimserver;

import java.util.ArrayList;
import java.util.List;

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
			
//		UserAllergy userAllergy = UserAllergy.builder().userId(225L).foodAllergy(foodAllergy).build();
//		userAllergyRepository.save(userAllergy);
	}
	
	@Test
	@Rollback(false)
	public void AllergyInputTest() {
		List<AllergyCategory> list = new ArrayList<>();
		list.add(AllergyCategory.builder().allergyName("갑각류").build());
		list.add(AllergyCategory.builder().allergyName("유제품").build());
		list.add(AllergyCategory.builder().allergyName("견과류").build());
		list.add(AllergyCategory.builder().allergyName("곡류").build());
		list.add(AllergyCategory.builder().allergyName("난류").build());
		list.add(AllergyCategory.builder().allergyName("육류").build());
		list.add(AllergyCategory.builder().allergyName("해산물").build());
		list.add(AllergyCategory.builder().allergyName("과일").build());
		list.add(AllergyCategory.builder().allergyName("채소").build());
		list.add(AllergyCategory.builder().allergyName("기타").build());
		
		for(AllergyCategory a : list) {
			allergyCategoryRepository.save(a);
		}
	}
	
	@Test
	@Rollback(false)
	public void FoodAllergyInputTest() {
		AllergyCategory allergyCategory1 = allergyCategoryRepository.findByAllergyName("유제품");
		AllergyCategory allergyCategory2 = allergyCategoryRepository.findByAllergyName("견과류");
		AllergyCategory allergyCategory3 = allergyCategoryRepository.findByAllergyName("곡류");
		AllergyCategory allergyCategory4 = allergyCategoryRepository.findByAllergyName("난류");
		AllergyCategory allergyCategory5 = allergyCategoryRepository.findByAllergyName("육류");
		AllergyCategory allergyCategory6 = allergyCategoryRepository.findByAllergyName("해산물");
		AllergyCategory allergyCategory7 = allergyCategoryRepository.findByAllergyName("과일");
		AllergyCategory allergyCategory8 = allergyCategoryRepository.findByAllergyName("채소");
		AllergyCategory allergyCategory9 = allergyCategoryRepository.findByAllergyName("기타");
		
		List<FoodAllergy> list = new ArrayList<>();
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory1).allergyMaterial("우유").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory1).allergyMaterial("게").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory2).allergyMaterial("아몬드").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory2).allergyMaterial("잣").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory2).allergyMaterial("호두").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory2).allergyMaterial("땅콩").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory3).allergyMaterial("대두").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory3).allergyMaterial("밀").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory3).allergyMaterial("메밀").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory4).allergyMaterial("메추리알").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory4).allergyMaterial("난류").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory4).allergyMaterial("계란").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory5).allergyMaterial("소고기").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory5).allergyMaterial("닭고기").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory5).allergyMaterial("쇠고기").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory5).allergyMaterial("돼지고기").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory6).allergyMaterial("오징어").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory6).allergyMaterial("조개류").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory7).allergyMaterial("복숭아").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory8).allergyMaterial("토마토").build());
		list.add(FoodAllergy.builder().allergyCategory(allergyCategory9).allergyMaterial("아황산류").build());
		
		for(FoodAllergy a : list) {
			foodAllergyRepository.save(a);
		}
	}
}
