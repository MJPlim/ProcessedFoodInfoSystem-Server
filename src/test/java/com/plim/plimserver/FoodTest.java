package com.plim.plimserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.plim.plimserver.domain.favorite.domain.Favorite;
import com.plim.plimserver.domain.favorite.repository.FavoriteRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.domain.FoodDetail;
import com.plim.plimserver.domain.food.domain.FoodImage;
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

@DataJpaTest
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


	
	
	@Test
	@Rollback(false)
	public void foodCreateTest() {
		FoodImage foodImage = FoodImage.builder().foodImageAddress("경로").foodMeteImageAddress("경로2").build();
		FoodDetail foodDetail = FoodDetail.builder().capacity("용량테스트").expriationDate("유통기한테스트").materials("원재료테스트")
				.nutrient("영양소테스트").build();
		Food food = Food.builder().foodName("테스트푸드2").reportNumber("테스트보고번호").category("테스트카테고리")
				.manufacturerName("테스트제조사").foodDetail(foodDetail).foodImage(foodImage).allergyMaterials("알러지 재료테스트")
				.barcodeNumber("바코드 번호0ㅑ09ㅑ090").build();
		foodRepository.save(food);
		
		Optional<User> user = userRepository.findById(120L);

//		Review review = Review.builder().user(user.get()).food(food).reviewRating(4).reviewDescription("리뷰테스트")
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

		Optional<Food> findFood = foodRepository.findById(8L); // 아이디 바꿔줘야함
		System.out.println(findFood.get().getFoodName());

		assertThat(findFood.get().getFoodName()).isEqualTo("테스트푸드2");
		assertThat(findFood.get().getBarcodeNumber()).isEqualTo("바코드 번호0ㅑ09ㅑ090");
		assertThat(findFood.get().getReportNumber()).isEqualTo("테스트보고번호");
		assertThat(findFood.get().getCategory()).isEqualTo("테스트카테고리");
		assertThat(findFood.get().getManufacturerName()).isEqualTo("테스트제조사");
		assertThat(findFood.get().getAllergyMaterials()).isEqualTo("알러지 재료테스트");


		assertThat(findFood.get().getFoodDetail().getCapacity()).isEqualTo("용량테스트");
		assertThat(findFood.get().getFoodDetail().getExpriationDate()).isEqualTo("유통기한테스트");
		assertThat(findFood.get().getFoodDetail().getMaterials()).isEqualTo("원재료테스트");
		assertThat(findFood.get().getFoodDetail().getNutrient()).isEqualTo("영양소테스트");

		assertThat(findFood.get().getFoodImage().getFoodImageAddress()).isEqualTo("경로");
		assertThat(findFood.get().getFoodImage().getFoodMeteImageAddress()).isEqualTo("경로2");
	}

	@Test
	@Rollback(false)
	public void reviewReadTest() {
		em.flush();
		em.clear();
		Optional<Review> findReview = reviewRepository.findById(6L);
		System.out.println("리뷰와 연결된 음식 이름 : " + findReview.get().getFood().getFoodName());

		assertThat(findReview.get().getReviewDescription()).isEqualTo("리뷰테스트");
		assertThat(findReview.get().getReviewRating()).isEqualTo(4.5f);

	}

	@Test
	@Rollback(false)
	public void favoriteReadTest() {
		em.flush();
		em.clear();
		Optional<Favorite> findFavorite = favoriteRepository.findById(6L);
		System.out.println("즐겨찾기와 연결된 음식 이름 : " + findFavorite.get().getFood().getFoodName());

		assertThat(findFavorite.get().getFood().getFoodName()).isEqualTo("테스트푸드2");
//		assertThat(findFavorite.get().getUserId()).isEqualTo(120L);
	}

	@Test
	@Rollback(false)
	public void reviewLikeReadTest() {
		em.flush();
		em.clear();
		Optional<ReviewLike> findReviewLike = reviewLikeRepository.findById(5L);
		System.out.println("리뷰좋아요와 연결된 리뷰 내용 : " + findReviewLike.get().getReview().getReviewDescription());

		assertThat(findReviewLike.get().getReview().getReviewDescription()).isEqualTo("리뷰테스트");
		assertThat(findReviewLike.get().getUserId()).isEqualTo(120L);
	}

	@Test
	@Rollback(false)
	public void reviewUpdateTest() {
		em.flush();
		em.clear();
		
		Optional<Review> findReview = reviewRepository.findById(6L);
		findReview.get().reviewUpdate(3, "맛이 바뀌었네요ㅎㅎ");
		findReview.get().reviewStateUpdate(ReviewStateType.DELETED);
	}
	
	@Test
	@Rollback(false)
	public void foodDeleteTest() {						//사실상 식품이 지워질일은 없겠지만 만들어봄 식품을 지우면 하위 리뷰, 즐찾, 좋아요 다 지워짐
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
