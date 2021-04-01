package com.plim.plimserver.food.controller;

import com.plim.plimserver.food.dto.FoodDTO;
import com.plim.plimserver.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/findFood/list")
    public ArrayList<FoodDTO> getFoodInfo(@RequestParam(name = "foodName") String foodName, @RequestParam(name = "pageNo") int pageNo){
        return this.foodService.findFood(foodName, pageNo);
    }

}
