package com.plim.plimserver.domain.advertisement.api;

import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;
import com.plim.plimserver.domain.advertisement.service.AdvertisementService;
import io.swagger.annotations.Api;
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

    @GetMapping("/ads")
    public ArrayList<AdvertisementResponse> getAdvertisementFoodList() {
        return this.advertisementService.getAdvertisementFoodList();
    }

    @PostMapping("/choosing")
    public boolean chooseAdvertisement(@RequestParam(name = "firstID") Long id1
            , @RequestParam(name = "secondID") Long id2, @RequestParam(name = "thirdID") Long id3) {
        try {
            return this.advertisementService.selectAdvertisement(id1, id2, id3);
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
