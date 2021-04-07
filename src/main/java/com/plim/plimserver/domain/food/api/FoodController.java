package com.plim.plimserver.domain.food.api;

import com.plim.plimserver.domain.food.dto.FoodDTO;
import com.plim.plimserver.domain.food.service.FoodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Api(tags = {"Food"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @ApiOperation(value = "제품이름 조회", notes = "제품이름과 일치하는 것을 조회한다")
    @GetMapping("/findFood/foodName")
    public ArrayList<FoodDTO> getFoodInfoByFoodName(@RequestParam(name = "foodName") String foodName, @RequestParam(name = "pageNo") int pageNo){
        return this.foodService.findFoodByFoodName(foodName, pageNo);
    }

    @ApiOperation(value = "회사이름 조회", notes = "회사이름과 일치하는 것을 조회한다")
    @GetMapping("/findFood/bsshName")
    public ArrayList<FoodDTO> getFoodInfoByBsshName(@RequestParam(name = "bsshName") String bsshName, @RequestParam(name = "pageNo") int pageNo) {
        return this.foodService.findFoodByBsshName(bsshName, pageNo);
    }

}
