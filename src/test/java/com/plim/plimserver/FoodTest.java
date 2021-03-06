package com.plim.plimserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.plim.plimserver.domain.favorite.domain.Favorite;
import com.plim.plimserver.domain.favorite.repository.FavoriteRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.domain.FoodDetail;
import com.plim.plimserver.domain.food.domain.FoodImage;
import com.plim.plimserver.domain.food.exception.FoodExceptionMessage;
import com.plim.plimserver.domain.food.exception.NoFoodDetailException;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.review.domain.ReviewLike;
import com.plim.plimserver.domain.review.domain.ReviewStateType;
import com.plim.plimserver.domain.review.dto.CreateReviewRequest;
import com.plim.plimserver.domain.review.repository.ReviewLikeRepository;
import com.plim.plimserver.domain.review.repository.ReviewRepository;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.DatabaseConfig;

@SpringBootTest
@Import(DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FoodTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private ReviewLikeRepository reviewLikeRepository;

	@Autowired
	private UserRepository userRepository;

//	@Test
//	@Rollback(false)
//	@Transactional
//	public void dataProcessing() {
//		List<Food> food = foodRepository.findByManufacturerName("??????")
//				.orElseThrow(() -> new NoFoodDetailException(FoodExceptionMessage.NO_FOOD_EXCEPTION_MESSAGE));
//
//		for(Food f : food) {
//			String s = f.getFoodDetail().getNutrient().replace(",", "`");
//			s.replace("???", "`");
//			f.getFoodDetail().newNutrient(s.toString());
//		}
//
//		String[] str = food.get(18).getFoodDetail().getNutrient().split("`");
//
//		for (String st : str) {
//			System.out.println(st.trim());
//		}
//
//	}

	@Test
	@Rollback(false)
	public void foodCreateTest() {
		FoodImage foodImage = FoodImage.builder().foodImageAddress("??????").foodMeteImageAddress("??????2").build();
		FoodDetail foodDetail = FoodDetail.builder().capacity("???????????????").expriationDate("?????????????????????").materials("??????????????????")
				.nutrient("??????????????????").build();
		Food food = Food.builder().foodName("???????????????2").reportNumber("?????????????????????").category("?????????????????????")
				.manufacturerName("??????????????????").foodDetail(foodDetail).foodImage(foodImage).allergyMaterials("????????? ???????????????")
				.barcodeNumber("????????? ??????0???09???090").build();
		foodRepository.save(food);

		Optional<User> user = userRepository.findById(120L);

//		Review review = Review.builder().user(user.get()).food(food).reviewRating(4).reviewDescription("???????????????")
//				.state(ReviewStateType.NORMAL).build();
		Review review = Review.of(user.get(), food, new CreateReviewRequest());
		reviewRepository.save(review);

//		Favorite favorite = Favorite.builder().userId(120L).food(food).build();
//		favoriteRepository.save(favorite);

		ReviewLike reviewLike = ReviewLike.builder().review(review).userId(120L).build();
		reviewLikeRepository.save(reviewLike);

	}

	@Test
	@Rollback(false)
	public void foodReadTest() {
		em.flush();
		em.clear();

		Optional<Food> findFood = foodRepository.findById(8L); // ????????? ???????????????
		System.out.println(findFood.get().getFoodName());

		assertThat(findFood.get().getFoodName()).isEqualTo("???????????????2");
		assertThat(findFood.get().getBarcodeNumber()).isEqualTo("????????? ??????0???09???090");
		assertThat(findFood.get().getReportNumber()).isEqualTo("?????????????????????");
		assertThat(findFood.get().getCategory()).isEqualTo("?????????????????????");
		assertThat(findFood.get().getManufacturerName()).isEqualTo("??????????????????");
		assertThat(findFood.get().getAllergyMaterials()).isEqualTo("????????? ???????????????");

		assertThat(findFood.get().getFoodDetail().getCapacity()).isEqualTo("???????????????");
		assertThat(findFood.get().getFoodDetail().getExpriationDate()).isEqualTo("?????????????????????");
		assertThat(findFood.get().getFoodDetail().getMaterials()).isEqualTo("??????????????????");
		assertThat(findFood.get().getFoodDetail().getNutrient()).isEqualTo("??????????????????");

		assertThat(findFood.get().getFoodImage().getFoodImageAddress()).isEqualTo("??????");
		assertThat(findFood.get().getFoodImage().getFoodMeteImageAddress()).isEqualTo("??????2");
	}

	@Test
	@Rollback(false)
	public void reviewReadTest() {
		em.flush();
		em.clear();
		Optional<Review> findReview = reviewRepository.findById(6L);
		System.out.println("????????? ????????? ?????? ?????? : " + findReview.get().getFood().getFoodName());

		assertThat(findReview.get().getReviewDescription()).isEqualTo("???????????????");
		assertThat(findReview.get().getReviewRating()).isEqualTo(4.5f);

	}

	@Test
	@Rollback(false)
	public void favoriteReadTest() {
		em.flush();
		em.clear();
		Optional<Favorite> findFavorite = favoriteRepository.findById(6L);
		System.out.println("??????????????? ????????? ?????? ?????? : " + findFavorite.get().getFood().getFoodName());

		assertThat(findFavorite.get().getFood().getFoodName()).isEqualTo("???????????????2");
//		assertThat(findFavorite.get().getUserId()).isEqualTo(120L);
	}

	@Test
	@Rollback(false)
	public void reviewLikeReadTest() {
		em.flush();
		em.clear();
		Optional<ReviewLike> findReviewLike = reviewLikeRepository.findById(5L);
		System.out.println("?????????????????? ????????? ?????? ?????? : " + findReviewLike.get().getReview().getReviewDescription());

		assertThat(findReviewLike.get().getReview().getReviewDescription()).isEqualTo("???????????????");
		assertThat(findReviewLike.get().getUserId()).isEqualTo(120L);
	}

	@Test
	@Rollback(false)
	public void reviewUpdateTest() {
		em.flush();
		em.clear();

		Optional<Review> findReview = reviewRepository.findById(6L);
		findReview.get().reviewUpdate(3, "?????? ?????????????????????");
		findReview.get().reviewStateUpdate(ReviewStateType.DELETED);
	}

	@Test
	@Rollback(false)
	public void foodDeleteTest() { // ????????? ????????? ??????????????? ???????????? ???????????? ????????? ????????? ?????? ??????, ??????, ????????? ??? ?????????
		em.flush();
		em.clear();

		Optional<Food> findFood = foodRepository.findById(7L);
		foodRepository.delete(findFood.get());

		Optional<Food> deletedFood = foodRepository.findById(7L);

		assertFalse(deletedFood.isPresent());
	}

	@Test
	@Rollback(false)
	public void reviewDeleteTest() {
		em.flush();
		em.clear();

		Optional<Review> findReview = reviewRepository.findById(6L);
		reviewRepository.delete(findReview.get());

		Optional<Review> deletedReview = reviewRepository.findById(6L);

		em.flush();
		em.clear();
		assertFalse(deletedReview.isPresent());
	}

	@Test
	@Rollback(false)
	public void favoriteDeleteTest() {
		em.flush();
		em.clear();

		Optional<Favorite> findFavorite = favoriteRepository.findById(6L);
		favoriteRepository.delete(findFavorite.get());

		Optional<Favorite> deletedFavorite = favoriteRepository.findById(6L);

		em.flush();
		em.clear();
		assertFalse(deletedFavorite.isPresent());
	}

	@Test
	@Rollback(false)
	public void reviewLikeDeleteTest() {
		em.flush();
		em.clear();

		Optional<ReviewLike> findReviewLike = reviewLikeRepository.findById(5L);
		reviewLikeRepository.delete(findReviewLike.get());

		Optional<ReviewLike> deletedReviewLike = reviewLikeRepository.findById(5L);

		em.flush();
		em.clear();
		assertFalse(deletedReviewLike.isPresent());
	}
}
