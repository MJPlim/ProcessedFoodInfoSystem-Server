package com.plim.plimserver.domain.food.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.plim.plimserver.domain.favorite.domain.Favorite;
import com.plim.plimserver.domain.review.domain.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "food_id")
	private Long id;

	@Column(name = "food_name", nullable = false)
	private String foodName;

	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true) // 식품 데이터가 지워질일은 거의 없겠지만 지워진다면 리뷰도 삭제
	private List<Review> reviewList = new ArrayList<>();

	@OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Favorite> favoriteList = new ArrayList<>();

	@Column(name = "report_no")
	private String reportNumber;

	@Column(name = "category")
	private String category; // 분류해서 이넘값으로 받아올수도?

	@Column(name = "manufacturer_name")
	private String manufacturerName;



	@Embedded
	private FoodDetail foodDetail; // 너무많아서 세부사항은 임베디드타입으로 설정

	@Embedded
	private FoodImage foodImage;

//	@Column(name = "expiration_date")
//	private String expriationDate;
//
//	@Lob
//	@Column(name = "materials")
//	private String materials;
//
//	@Lob
//	@Column(name = "nutrient")
//	private String nutrient;
//
//	@Column(name = "capacity")
//	private String capacity;
//
	@Column(name = "allergy_materials")
	private String allergyMaterials;

//	@Column(name = "food_image_address")
//	private String foodImageAddress;
//
//	@Column(name = "food_mete_image_address")
//	private String foodMeteImageAddress;

	@Column(name = "barcode_no")
	private String barcodeNumber;

	@Column(name = "view_count")
	@ColumnDefault("0")
	private Long view;

	// 이 아래부터는 확정된 데이터가 아니라 지워질 수 있다.
	@Column(name = "release_date")
	private String releaseDate;

	@Column(name = "price")
	private Integer price;

	@Column(name = "license_no")
	private String licenseNumber;

	@Builder
	public Food(String foodName, String reportNumber, String category, String manufacturerName,
			String allergyMaterials, FoodDetail foodDetail, FoodImage foodImage, String barcodeNumber, String releaseDate, Integer price,
			String licenseNumber) {
		this.foodName = foodName;
		this.reportNumber = reportNumber;
		this.category = category;
		this.manufacturerName = manufacturerName;
		this.foodDetail = foodDetail;
		this.allergyMaterials = allergyMaterials;
		this.foodImage = foodImage;
		this.barcodeNumber = barcodeNumber;
		this.releaseDate = releaseDate;
		this.price = price;
		this.licenseNumber = licenseNumber;
	}

}
