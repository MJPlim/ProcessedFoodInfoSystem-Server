package com.plim.plimserver.food.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plim.plimserver.food.api.ApiKey;
import com.plim.plimserver.food.api.ApiKeyRepository;
import com.plim.plimserver.food.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


    public ArrayList<FoodDTO> findFood(String foodName, int pageNo){
        ArrayList<FoodDTO> foodList = new ArrayList<>();
        int page = 1 + 5*(pageNo-1);

        Random r = new Random();
        int id = r.nextInt(3)+1;
        Optional<ApiKey> optionRawKey = this.apiKeyRepository.findById(id);
        String rawMaterialKey = optionRawKey.orElseThrow(NoSuchElementException::new).getKeyValue();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String str = restTemplate.getForObject(rawMaterialURL + rawMaterialKey + "/" + this.apiCode
                + "/json/" + page + "/" + (page+4) + "/PRDLST_NM=" + foodName, String.class);

        JsonParser jsonParser = new JsonParser();
        JsonObject obj = (JsonObject)((JsonObject) jsonParser.parse(str)).get(this.apiCode);// "C002" key의 value 가져오기
        Optional<JsonArray> optionalRow = Optional.ofNullable((JsonArray) obj.get("row"));// "row" key의 array 가져오기
        JsonArray arr = optionalRow.orElseThrow(NullPointerException::new);

        for (int j = 0; j < arr.size(); j++) {
            JsonObject o = (JsonObject) arr.get(j);
            FoodDTO food = FoodDTO.builder()
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
