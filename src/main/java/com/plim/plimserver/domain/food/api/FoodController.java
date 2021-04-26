package com.plim.plimserver.domain.food.api;

import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.domain.food.service.FoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Api(tags = {"Food"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @ApiOperation(value = "제품이름 조회", notes = "제품이름과 일치하는 것을 조회한다")
    @GetMapping("/findFood/foodName")
    public ArrayList<FoodResponse> getFoodInfoByFoodName(@RequestParam(name = "foodName") String foodName) {
        return this.foodService.findFoodByFoodName(foodName);
    }

    @ApiOperation(value = "회사이름 조회", notes = "회사이름과 일치하는 것을 조회한다")
    @GetMapping("/findFood/manufacturerName")
    public ArrayList<FoodResponse> getFoodInfoByManufacturerName(@RequestParam(name = "manufacturerName") String manufacturerName) {
        return this.foodService.findFoodByManufacturerName(manufacturerName);
    }
    
    @ApiOperation(value = "특정 제품의 상세정보 조회", notes = "선택한 제품을 조회하여 상세정보를 반환한다")
    @GetMapping("findFood/foodDetail")
    public FoodDetailResponse getFoodDetail(@RequestParam(name = "foodId") Long foodId) {
        return this.foodService.getFoodDetail(foodId);
    }

    @ApiOperation(value = "HACCP API를 이용한 food 테이블 생성", notes = "HACCP API로 데이터를 제공받아 데이터베이스의 food 테이블에 데이터를 생성")
    @GetMapping("/makeFoodDB")
    public int makeFoodDB() {
        return this.foodService.makeFoodDatabaseWithoutBarCodeAPI();
    }
}
