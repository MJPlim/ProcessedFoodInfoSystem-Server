package com.plim.plimserver.domain.food.api;

import com.plim.plimserver.domain.food.dto.FindFoodByBarcodeRequest;
import com.plim.plimserver.domain.food.dto.FindFoodBySortingResponse;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.domain.food.service.FoodService;
import com.plim.plimserver.global.dto.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Food"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @ApiOperation(value = "특정 제품의 상세정보 조회", notes = "선택한 제품을 조회하여 상세정보를 반환한다")
    @GetMapping("/findFood/foodDetail")
    public FoodDetailResponse getFoodDetail(@RequestParam(name = "foodId") Long foodId) {
        return this.foodService.getFoodDetail(foodId);
    }

    @ApiOperation(value = "바코드 번호를 이용한 제품 조회", notes = "바코드 번호를 통해 제품 상세 정보를 제공한다.")
    @PostMapping("/findFood/barcode")
    public ResponseEntity<FoodDetailResponse> findFoodByBarcode(@RequestBody FindFoodByBarcodeRequest request) {
        return ResponseEntity.ok(this.foodService.findFoodByBarcode(request.getBarcode()));
    }

    @ApiOperation(value = "검색결과 조건에 따라 정렬", notes = "검색결과를 조건에 따라 정렬하여 제공한다")
    @GetMapping("/getFoodListBySorting")
    public ResponseEntity<FindFoodBySortingResponse> getFoodListBySorting(
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false) String sortElement,
            @RequestParam(name = "foodName", required = false) String foodName,
            @RequestParam(name = "manufacturerName", required = false) String manufacturerName) {
        return ResponseEntity.ok(this.foodService.findFoodByPaging(
                pageNo, size, sortElement, foodName, manufacturerName));
    }

    @ApiOperation(value = "카테고리에 해당하는 제품 조회", notes = "카테고리에 해당하는 제품을 제공한다.")
    @GetMapping("/list/category")
    public ResponseEntity<Pagination<List<FoodResponse>>> getFoodListByCategory(@PageableDefault Pageable pageable,
                                                                                @RequestParam String category) {
        return ResponseEntity.ok(this.foodService.findFoodByCategory(category, pageable));
    }

    //    @ApiOperation(value = "HACCP API를 이용한 food 테이블 생성", notes = "HACCP API로 데이터를 제공받아 데이터베이스의 food 테이블에 데이터를 생성")
//    @GetMapping("/makeFoodDB")
//    public int makeFoodDB() {
//        return this.foodService.makeFoodDatabaseWithoutBarCodeAPI();
//    }

}
