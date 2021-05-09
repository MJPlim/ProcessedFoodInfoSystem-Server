package com.plim.plimserver.domain.food.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plim.plimserver.domain.api.domain.ApiKey;
import com.plim.plimserver.domain.api.repository.ApiKeyRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.domain.FoodDetail;
import com.plim.plimserver.domain.food.domain.FoodImage;
import com.plim.plimserver.domain.food.domain.SortElement;
import com.plim.plimserver.domain.food.dto.FindFoodBySortingResponse;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.domain.food.exception.FoodExceptionMessage;
import com.plim.plimserver.domain.food.exception.NoFoodDetailException;
import com.plim.plimserver.domain.food.exception.NoFoodListException;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.global.dto.Pagination;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    private final ApiKeyRepository apiKeyRepository;
    private final RestTemplate restTemplate;
    private String rawMaterialURL = "http://openapi.foodsafetykorea.go.kr/api/";
    private String haccpdataURL = "http://apis.data.go.kr/B553748/CertImgListService/getCertImgListService?ServiceKey=fTEm%2FiVcJFgwgjEeDhMET1kFQZduiSF09BedQaKgQRGH7fWSoKITTfTFZH2EzYono62%2BwMlAxdy2Jj64qzpgqQ%3D%3D&returnType=json&numOfRows=100";
    private String apiCode = "C002";
    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(ApiKeyRepository apiKeyRepository, RestTemplate restTemplate, FoodRepository foodRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.restTemplate = restTemplate;
        this.foodRepository = foodRepository;
    }

    @Override
    public ArrayList<FoodResponse> findFoodByFoodName(String foodName) {
        ArrayList<FoodResponse> foodList = new ArrayList<>();
        List<Food> foods = this.foodRepository.findAllByFoodNameContaining(foodName);
        for (Food food : foods) {
            foodList.add(FoodResponse.builder()
                    .foodId(food.getId())
                    .foodName(food.getFoodName())
                    .category(food.getCategory())
                    .manufacturerName(food.getManufacturerName())
                    .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                    .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                    .build());
        }
        return foodList;
    }

    @Override
    public ArrayList<FoodResponse> findFoodByManufacturerName(String manufacturerName) {
        ArrayList<FoodResponse> foodList = new ArrayList<>();
        List<Food> foods = this.foodRepository.findAllByManufacturerNameContaining(manufacturerName);
        for (Food food : foods) {
            foodList.add(FoodResponse.builder()
                    .foodId(food.getId())
                    .foodName(food.getFoodName())
                    .category(food.getCategory())
                    .manufacturerName(food.getManufacturerName())
                    .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                    .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                    .build());
        }
        return foodList;
    }

    private String getFoodSafetyKoreaApiKey() {
        Random r = new Random();
        int id = r.nextInt(3) + 1;
        List<ApiKey> apiKeys = this.apiKeyRepository.findAllByKeyName("foodsafetykorea");
        Optional<ApiKey> optionalApiKey = apiKeys.stream().filter(k -> k.getId() == id).findAny();// API Key를 랜덤하게 하여 key를 돌려써서 가져옴
        return optionalApiKey.orElseThrow(NoSuchElementException::new).getKeyValue();
    }

    private JsonArray parseRawMaterialApiArray(String resultJson) {
        //JsonParser를 이용하여 json의 구조 분리
        JsonParser jsonParser = new JsonParser();
        JsonObject obj = (JsonObject) ((JsonObject) jsonParser.parse(resultJson)).get(this.apiCode);// "C002" key의 value 가져오기
        Optional<JsonArray> optionalRow = Optional.ofNullable((JsonArray) obj.get("row"));// "row" key의 array 가져오기
        return optionalRow.orElseThrow(NullPointerException::new);
    }

    @Transactional
    @Override
    public FoodDetailResponse getFoodDetail(Long foodId) {
        Optional<Food> optionalFood = this.foodRepository.findById(foodId);
        Food food = optionalFood.orElseThrow(() -> new NoFoodDetailException(FoodExceptionMessage.NO_FOOD_EXCEPTION_MESSAGE));
        food.setViewCount(food.getViewCount() + 1);
        return FoodDetailResponse.builder()
                .foodId(food.getId())
                .foodName(food.getFoodName())
                .category(food.getCategory())
                .manufacturerName(food.getManufacturerName())
                .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                .materials(food.getFoodDetail().getMaterials())
                .nutrient(food.getFoodDetail().getNutrient())
                .allergyMaterials(food.getAllergyMaterials())
                .viewCount(food.getViewCount())
                .build();
    }

    @SneakyThrows
    @Override
    public int makeFoodDatabaseWithoutBarCodeAPI() {
        for (int t = 0; t < 154; t++) {
            String url = haccpdataURL + "&pageNo=" + (t + 1);
            URI uri = new URI(url); // service key % -> 25 encoding 방지
            String jsonString = restTemplate.getForObject(uri, String.class);

            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(jsonString).getAsJsonObject();
            JsonArray array = object.get("list").getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                JsonObject o = array.get(i).getAsJsonObject();
                this.foodRepository.save(Food.builder()
                        .foodName(getJsonData(o, "prdlstNm"))
                        .reportNumber(getJsonData(o, "prdlstReportNo"))
                        .category(getJsonData(o, "prdkind"))
                        .manufacturerName(getJsonData(o, "manufacture"))
                        .foodDetail(FoodDetail.builder()
                                .materials(getJsonData(o, "rawmtrl"))
                                .nutrient(getJsonData(o, "nutrient"))
                                .capacity(getJsonData(o, "capacity"))
                                .build())
                        .foodImage(FoodImage.builder().foodImageAddress(getJsonData(o, "imgurl1"))
                                .foodMeteImageAddress(getJsonData(o, "imgurl2")).build())
                        .allergyMaterials(getJsonData(o, "allergy"))
                        .barcodeNumber(getJsonData(o, "barcode"))
                        .build());
            }
        }
        return 1;
    }

    private String getJsonData(JsonObject o, String key) {
        JsonElement reportNoObject = o.get(key);
        String result = "No data";
        if (reportNoObject != null) {
            result = reportNoObject.getAsString();
        }
        return result;
    }

    @Override
    public FoodDetailResponse findFoodByBarcode(String barcode) {
        return FoodDetailResponse.from(this.foodRepository.findByBarcodeNumber(barcode)
                .orElseThrow(() -> new NoFoodDetailException(FoodExceptionMessage.NO_FOOD_DETAIL_EXCEPTION_MESSAGE)));
    }

    @Override
    public FindFoodBySortingResponse findFoodByPaging(int pageNo, int size, String sortElement, String foodName, String manufacturerName) {
        if (sortElement == null) {
            Pageable page = PageRequest.of(pageNo - 1, size);
            if (foodName != null) {
                Page<Food> foodPage = this.foodRepository.findAllByFoodNameContaining(foodName, page);
                return this.makeFoodResponseByPaging(foodPage);
            } else if (manufacturerName != null) {
                Page<Food> foodPage = this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, page);
                return this.makeFoodResponseByPaging(foodPage);
            } else {
                throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
            }
        } else {
            if (sortElement.equals(SortElement.RANK.getMessage())) { // 아직 안함
                return null;
            } else if (sortElement.equals(SortElement.MANUFACTURER.getMessage())) {
                Pageable page = PageRequest.of(pageNo - 1, size, Sort.by("manufacturerName"));
                if (foodName != null) {
                    Page<Food> foodPage = this.foodRepository.findAllByFoodNameContaining(foodName, page);
                    return this.makeFoodResponseByPaging(foodPage);
                } else if (manufacturerName != null) {
                    Page<Food> foodPage = this.foodRepository.findAllByManufacturerNameContaining(manufacturerName, page);
                    return this.makeFoodResponseByPaging(foodPage);
                } else {
                    throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
                }
            } else if (sortElement.equals(SortElement.REVIEW_COUNT.getMessage())) {
                if (foodName != null) {
                    List<Food> foodPage = this.foodRepository.findAllByFoodNameContaining(foodName);
                    foodPage.sort((o1, o2) -> {
                        if (o1.getReviewList().size() < o2.getReviewList().size()) {
                            return -1;
                        } else if (o1.getReviewList().size() > o2.getReviewList().size()) {
                            return 1;
                        }
                        return 0;
                    });

                    return this.makeFoodResponse(foodPage, pageNo, size);
                } else if (manufacturerName != null) {
                    List<Food> foodPage = this.foodRepository.findAllByManufacturerNameContaining(manufacturerName);
                    foodPage.sort((o1, o2) -> {
                        if (o1.getReviewList().size() < o2.getReviewList().size()) {
                            return -1;
                        } else if (o1.getReviewList().size() > o2.getReviewList().size()) {
                            return 1;
                        }
                        return 0;
                    });
                    return this.makeFoodResponse(foodPage, pageNo, size);
                } else {
                    throw new NoFoodListException(FoodExceptionMessage.NO_FOOD_LIST_EXCEPTION_MESSAGE);
                }
            }

        }
        return null;
    }

    private FindFoodBySortingResponse makeFoodResponseByPaging(Page<Food> foodList) {
        List<FoodResponse> resultList = new ArrayList<>();
        for (Food food : foodList.getContent()) {
            FoodResponse response = FoodResponse.builder()
                    .foodId(food.getId())
                    .foodName(food.getFoodName())
                    .category(food.getCategory())
                    .manufacturerName(food.getManufacturerName())
                    .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                    .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                    .build();
            resultList.add(response);
        }
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
                    FoodResponse response = FoodResponse.builder()
                            .foodId(food.getId())
                            .foodName(food.getFoodName())
                            .category(food.getCategory())
                            .manufacturerName(food.getManufacturerName())
                            .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                            .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                            .build();
                    resultList.add(response);
                }
            } else {
                for (int i = startIndex; i < size * pageNo; i++) {
                    Food food = foodList.get(i);
                    FoodResponse response = FoodResponse.builder()
                            .foodId(food.getId())
                            .foodName(food.getFoodName())
                            .category(food.getCategory())
                            .manufacturerName(food.getManufacturerName())
                            .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                            .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                            .build();
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
    public Pagination<List<FoodDetailResponse>> findFoodByCategory(String category, Pageable pageable) {
        Page<Food> page = this.foodRepository.findAllByCategoryContaining(category, pageable);
        List<FoodDetailResponse> data = page.stream()
                .map(FoodDetailResponse::from)
                .collect(Collectors.toList());
        return Pagination.of(page, data);
    }

}
