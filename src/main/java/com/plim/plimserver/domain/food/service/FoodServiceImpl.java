package com.plim.plimserver.domain.food.service;

import com.plim.plimserver.domain.allergy.repository.FoodAllergyRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.domain.food.exception.FoodExceptionMessage;
import com.plim.plimserver.domain.food.exception.NoFoodDetailException;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.global.dto.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Pagination<List<FoodResponse>> findFoodByPaging(int pageNo, int size, String sortElement, String foodName,
                                                      String manufacturerName, List<String> allergyList) {
        Pageable pageable = PageRequest.of(pageNo - 1, size);
        Page<Food> page = this.foodRepository.search(sortElement, foodName, manufacturerName, allergyList, pageable);
        List<FoodResponse> data = page.stream().map(FoodResponse::from).collect(Collectors.toList());
        return Pagination.of(page, data);
    }

    @Override
    public Pagination<List<FoodResponse>> findFoodByCategory(String category, int page, String sort, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<Food> foodPage = this.foodRepository.findAllByCategoryContaining(category, pageable);
        List<FoodResponse> data = foodPage.stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());
        return Pagination.of(foodPage, data);
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
