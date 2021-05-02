package com.plim.plimserver.domain.advertisement.api;

import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;
import com.plim.plimserver.domain.advertisement.service.AdvertisementService;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Api(tags = {"Advertisement"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @ApiOperation(value = "광고제품 목록 랜덤 반환", notes = "광고상태가 on인 제품 중 3개를 랜덤하게 반환한다")
    @GetMapping("/ads")
    public ArrayList<AdvertisementResponse> getAdvertisementFoodList() {
        return this.advertisementService.getAdvertisementFoodList();
    }

    @ApiOperation(value = "광고용 특정 제품의 상세정보 조회", notes = "선택한 광고 제품을 조회하여 상세정보를 반환한다")
    @GetMapping("/foodDetail")
    public FoodDetailResponse getFoodDetailForAdvertisement(@RequestParam(name = "adId") Long adId) {
        return this.advertisementService.getFoodDetailForAdvertisement(adId);
    }

    @ApiOperation(value = "광고제품을 db에 생성", notes = "ad_food 테이블에 광고할 제품을 넣는다")
    @PostMapping("/onItem")
    public boolean chooseAdvertisement(@RequestParam(name = "firstID") Long id1
            , @RequestParam(name = "secondID", required = false) Long id2, @RequestParam(name = "thirdID", required = false) Long id3) {
        try {
            return this.advertisementService.selectAdvertisement(id1, id2, id3);
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
