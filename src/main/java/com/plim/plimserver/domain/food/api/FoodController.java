package com.plim.plimserver.domain.food.api;

import com.plim.plimserver.domain.food.dto.FoodDTO;
import com.plim.plimserver.domain.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/findFood/foodName")
    public ArrayList<FoodDTO> getFoodInfoByFoodName(@RequestParam(name = "foodName") String foodName, @RequestParam(name = "pageNo") int pageNo){
        return this.foodService.findFoodByFoodName(foodName, pageNo);
    }

    @GetMapping("/findFood/bsshName")
    public ArrayList<FoodDTO> getFoodInfoByBsshName(@RequestParam(name = "bsshName") String bsshName, @RequestParam(name = "pageNo") int pageNo) {
        return this.foodService.findFoodByBsshName(bsshName, pageNo);
    }

}
