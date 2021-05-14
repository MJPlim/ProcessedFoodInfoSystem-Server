package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.allergy.domain.FoodAllergy;
import com.plim.plimserver.domain.allergy.domain.UserAllergy;
import com.plim.plimserver.domain.allergy.exception.AllergyExceptionMessage;
import com.plim.plimserver.domain.allergy.exception.NotFoundAllergyException;
import com.plim.plimserver.domain.allergy.repository.FoodAllergyRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.domain.SortElement;
import com.plim.plimserver.domain.food.dto.FindFoodBySortingResponse;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.domain.food.exception.FoodExceptionMessage;
import com.plim.plimserver.domain.food.exception.NoFoodDetailException;
import com.plim.plimserver.domain.food.exception.NoFoodListException;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.domain.food.util.SortByReviewCountAndDesc;
import com.plim.plimserver.domain.food.util.SortByReviewRateAndDesc;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import com.plim.plimserver.global.dto.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

@Service
public class FoodServiceImpl implements FoodService {
//    private final ApiKeyRepository apiKeyRepository;
//    private final RestTemplate restTemplate;
//    private String haccpdataURL = "http://apis.data.go.kr/B553748/CertImgListService/getCertImgListService?ServiceKey=fTEm%2FiVcJFgwgjEeDhMET1kFQZduiSF09BedQaKgQRGH7fWSoKITTfTFZH2EzYono62%2BwMlAxdy2Jj64qzpgqQ%3D%3D&returnType=json&numOfRows=100";
    private final FoodRepository foodRepository;
    private final FoodAllergyRepository foodAllergyRepository;
    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository, FoodAllergyRepository foodAllergyRepository) {
        this.foodRepository = foodRepository;
        this.foodAllergyRepository = foodAllergyRepository;
    }

    @Transactional
    @Override
    public FoodDetailResponse getFoodDetail(Long foodId) {
        Optional<Food> optionalFood = this.foodRepository.findById(foodId);
        Food food = optionalFood.orElseThrow(() -> new NoFoodDetailException(FoodExceptionMessage.NO_FOOD_EXCEPTION_MESSAGE));
        food.setViewCount(food.getViewCount() + 1);
        return FoodDetailResponse.from(food);
    }

    @Override
    public FoodDetailResponse findFoodByBarcode(String barcode) {
        return FoodDetailResponse.from(this.foodRepository.findByBarcodeNumber(barcode)
                .orElseThrow(() -> new NoFoodDetailException(FoodExceptionMessage.NO_FOOD_DETAIL_EXCEPTION_MESSAGE)));
    }

    @Override
    public FindFoodBySortingResponse findFoodByPaging(int pageNo, int size, String sortElement, String foodName, String manufacturerName, 
    		List<String> allergyList) {
    	
    	String allergys = "";
    	if(allergyList != null) {
    		for(String s : allergyList) {
        		foodAllergyRepository.findByAllergyMaterial(s)
        				.orElseThrow(() -> new NotFoundAllergyException(AllergyExceptionMessage.NOT_FOUND_ALLERGY_EXCEPTION_MESSAGE));
        	}
    		allergys = allergyList.stream().collect(Collectors.joining("|"));
    	}
    		System.out.println(allergys);
    	
        if (sortElement == null) {
            Pageable page = PageRequest.of(pageNo - 1, size);
            if (foodName != null && manufacturerName == null) {
                Page<Food> foodPage = allergyList == null?
                			this.foodRepository.findAllByFoodNameContaining(foodName, page):
                			this.foodRepository.findAllByFoodNameContaining(foodName, allergys, page);
                return this.makeFoodResponseByPaging(foodPage);
            } else if (manufacturerName != null && foodName == null) {
                Page<Food> foodPage = allergyList == null?
                		this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, page):
                		this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, allergys, page);
                return this.makeFoodResponseByPaging(foodPage);
            } else {
                throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
            }
        } else {
            if (sortElement.equals(SortElement.RANK.getMessage())) {
                if (foodName != null && manufacturerName == null) {
                    List<Food> foodPage = allergyList == null?
                			this.foodRepository.findAllByFoodNameContaining(foodName):
                			this.foodRepository.findAllByFoodNameContaining(foodName, allergys);
                    foodPage.sort(new SortByReviewRateAndDesc().thenComparing(new SortByReviewCountAndDesc()));
                    return this.makeFoodResponse(foodPage, pageNo, size);
                } else if (manufacturerName != null && foodName == null) {
                    List<Food> foodPage = allergyList == null?
                    		this.foodRepository.findAllByManufacturerNameContaining(manufacturerName):
                        	this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, allergys);
                    foodPage.sort(new SortByReviewRateAndDesc().thenComparing(new SortByReviewCountAndDesc()));
                    return this.makeFoodResponse(foodPage, pageNo, size);
                } else {
                    throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
                }
            } else if (sortElement.equals(SortElement.MANUFACTURER.getMessage())) {
                Pageable pageNotAl = PageRequest.of(pageNo - 1, size, Sort.by("manufacturerName"));
                Pageable pageAl = PageRequest.of(pageNo - 1, size, Sort.by("manufacturer_name"));
                if (foodName != null && manufacturerName == null) {
                    Page<Food> foodPage = allergyList == null?
                			this.foodRepository.findAllByFoodNameContaining(foodName, pageNotAl):
                			this.foodRepository.findAllByFoodNameContaining(foodName, allergys, pageAl);
                    return this.makeFoodResponseByPaging(foodPage);
                } else if (manufacturerName != null && foodName == null) {
                    Page<Food> foodPage = allergyList == null?
                    			this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, pageNotAl):
                        		this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, allergys, pageAl);
                    return this.makeFoodResponseByPaging(foodPage);
                } else {
                    throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
                }
            } else if (sortElement.equals(SortElement.REVIEW_COUNT.getMessage())) {
                if (foodName != null && manufacturerName == null) {
                    List<Food> foodPage = allergyList == null?
                			this.foodRepository.findAllByFoodNameContaining(foodName):
                			this.foodRepository.findAllByFoodNameContaining(foodName, allergys);
                    foodPage.sort(new SortByReviewCountAndDesc());
                    return this.makeFoodResponse(foodPage, pageNo, size);
                } else if (manufacturerName != null && foodName == null) {
                    List<Food> foodPage = allergyList == null?
                    		this.foodRepository.findAllByManufacturerNameContaining(manufacturerName):
                            this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, allergys);
                    foodPage.sort(new SortByReviewCountAndDesc());
                    return this.makeFoodResponse(foodPage, pageNo, size);
                } else {
                    throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
                }
            }

        }
        return null;
    }

    private FindFoodBySortingResponse makeFoodResponseByPaging(Page<Food> foodList) {
        List<FoodResponse> resultList = foodList.getContent().stream().map(FoodResponse::from).collect(Collectors.toList());
        return FindFoodBySortingResponse.builder()
                .pageNo(foodList.getNumber() + 1)
                .pageSize(foodList.getSize())
                .maxPage(foodList.getTotalPages())
                .totalDataCount((int) foodList.getTotalElements())
                .resultList(resultList)
                .build();
    }

    private FindFoodBySortingResponse makeFoodResponse(List<Food> foodList, int pageNo, int size) {
        List<FoodResponse> resultList = new ArrayList<>();
        int startIndex = size * (pageNo - 1);
        int maxPage = (foodList.size() / size + 1);
        int rest = foodList.size() % size;
        if (1 <= pageNo && pageNo <= maxPage) {
            if (pageNo == maxPage) {
                for (int i = startIndex; i < startIndex + rest; i++) {
                    Food food = foodList.get(i);
                    FoodResponse response = FoodResponse.from(food);
                    resultList.add(response);
                }
            } else {
                for (int i = startIndex; i < size * pageNo; i++) {
                    Food food = foodList.get(i);
                    FoodResponse response = FoodResponse.from(food);
                    resultList.add(response);
                }
            }
        }
        return FindFoodBySortingResponse.builder()
                .pageNo(pageNo)
                .pageSize(size)
                .maxPage(foodList.size() / size + 1)
                .totalDataCount(foodList.size())
                .resultList(resultList)
                .build();
    }

    @Override
    public Pagination<List<FoodResponse>> findFoodByCategory(String category, Pageable pageable) {
        Page<Food> page = this.foodRepository.findAllByCategoryContaining(category, pageable);
        List<FoodResponse> data = page.stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());
        return Pagination.of(page, data);
    }

//    @SneakyThrows
//    @Override
//    public int makeFoodDatabaseWithoutBarCodeAPI() {
//        for (int t = 0; t < 154; t++) {
//            String url = haccpdataURL + "&pageNo=" + (t + 1);
//            URI uri = new URI(url); // service key % -> 25 encoding 방지
//            String jsonString = restTemplate.getForObject(uri, String.class);
//
//            JsonParser parser = new JsonParser();
//            JsonObject object = parser.parse(jsonString).getAsJsonObject();
//            JsonArray array = object.get("list").getAsJsonArray();
//
//            for (int i = 0; i < array.size(); i++) {
//                JsonObject o = array.get(i).getAsJsonObject();
//                this.foodRepository.save(Food.builder()
//                                             .foodName(getJsonData(o, "prdlstNm"))
//                                             .reportNumber(getJsonData(o, "prdlstReportNo"))
//                                             .category(getJsonData(o, "prdkind"))
//                                             .manufacturerName(getJsonData(o, "manufacture"))
//                                             .foodDetail(FoodDetail.builder()
//                                                                   .materials(getJsonData(o, "rawmtrl"))
//                                                                   .nutrient(getJsonData(o, "nutrient"))
//                                                                   .capacity(getJsonData(o, "capacity"))
//                                                                   .build())
//                                             .foodImage(FoodImage.builder().foodImageAddress(getJsonData(o, "imgurl1"))
//                                                                 .foodMeteImageAddress(getJsonData(o, "imgurl2")).build())
//                                             .allergyMaterials(getJsonData(o, "allergy"))
//                                             .barcodeNumber(getJsonData(o, "barcode"))
//                                             .build());
//            }
//        }
//        return 1;
//    }

//    private String getJsonData(JsonObject o, String key) {
//        JsonElement reportNoObject = o.get(key);
//        String result = "No data";
//        if (reportNoObject != null) {
//            result = reportNoObject.getAsString();
//        }
//        return result;
//    }

}
