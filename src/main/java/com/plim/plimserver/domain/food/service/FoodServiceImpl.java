package com.plim.plimserver.domain.food.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plim.plimserver.domain.api.domain.ApiKey;
import com.plim.plimserver.domain.api.repository.ApiKeyRepository;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class FoodServiceImpl implements FoodService{
    private final ApiKeyRepository apiKeyRepository;
    private final RestTemplate restTemplate;
    private String rawMaterialURL = "http://openapi.foodsafetykorea.go.kr/api/";
    private String apiCode = "C002";

    @Autowired
    public FoodServiceImpl(ApiKeyRepository apiKeyRepository, RestTemplate restTemplate) {
        this.apiKeyRepository = apiKeyRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public ArrayList<FoodResponse> findFoodByFoodName (String foodName, int pageNo){
        int page = 1 + 5*(pageNo-1); // 결과를 5개씩 받기 위한 리스트번호
        String apiKey = getApiKey();

        // RestTemplate 클래스를 이용하여 url, 받아올 정보의 타입을 인자로 넘겨서 반환된 json값을 지정한 타입으로 가져옴.
        String jsonString = restTemplate.getForObject(rawMaterialURL + apiKey + "/" + this.apiCode
                + "/json/" + page + "/" + (page+4) + "/PRDLST_NM=" + foodName, String.class);
        JsonArray arr = parseRawMaterialApiArray(jsonString);

        return makeFoodDTOList(arr);
    }

    @Override
    public ArrayList<FoodResponse> findFoodByBsshName(String bsshName, int pageNo) {
        int page = 1 + 5*(pageNo-1); // 결과를 5개씩 받기 위한 리스트번호
        String apiKey = getApiKey();

        // RestTemplate 클래스를 이용하여 url, 받아올 정보의 타입을 인자로 넘겨서 반환된 json값을 지정한 타입으로 가져옴.
        String jsonString = restTemplate.getForObject(rawMaterialURL + apiKey + "/" + this.apiCode
                + "/json/" + page + "/" + (page+4) + "/BSSH_NM=" + bsshName, String.class);
        JsonArray arr = parseRawMaterialApiArray(jsonString);

        return makeFoodDTOList(arr);
    }

    private String getApiKey() {
        Random r = new Random();
        int id = r.nextInt(3)+1;
        Optional<ApiKey> optionApiKey = this.apiKeyRepository.findById(id); // API Key를 랜덤하게 하여 key를 돌려써서 가져옴
        return optionApiKey.orElseThrow(NoSuchElementException::new).getKeyValue();
    }

    private JsonArray parseRawMaterialApiArray(String resultJson) {
        //JsonParser를 이용하여 json의 구조 분리
        JsonParser jsonParser = new JsonParser();
        JsonObject obj = (JsonObject)((JsonObject) jsonParser.parse(resultJson)).get(this.apiCode);// "C002" key의 value 가져오기
        Optional<JsonArray> optionalRow = Optional.ofNullable((JsonArray) obj.get("row"));// "row" key의 array 가져오기
        return optionalRow.orElseThrow(NullPointerException::new);
    }

    private ArrayList<FoodResponse> makeFoodDTOList(JsonArray arr) {
        ArrayList<FoodResponse> foodList = new ArrayList<>(); // 결과를 담을 List
        for (int j = 0; j < arr.size(); j++) {
            JsonObject o = (JsonObject) arr.get(j);
            FoodResponse food = FoodResponse.builder()
                                            .lcnsNo(Long.parseLong(o.get("LCNS_NO").toString().replace("\"", "")))
                                            .bsshName(o.get("BSSH_NM").toString().replace("\"", ""))
                                            .prdlstReportNo(Long.parseLong(o.get("PRDLST_REPORT_NO").toString().replace("\"", "")))
                                            .prmsDate(Long.parseLong(o.get("PRMS_DT").toString().replace("\"", "")))
                                            .prdlstName(o.get("PRDLST_NM").toString().replace("\"", ""))
                                            .prdlstDCName(o.get("PRDLST_DCNM").toString().replace("\"", ""))
                                            .rawMaterialName(o.get("RAWMTRL_NM").toString().replace("\"", ""))
                                            .build();
            foodList.add(food);
        }
        return foodList;
    }
}
