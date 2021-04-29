package com.plim.plimserver.domain.advertisement.api;

import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;
import com.plim.plimserver.domain.advertisement.service.AdvertisementService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Api(tags = {"Advertisement"})
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @GetMapping("/ads")
    public ArrayList<AdvertisementResponse> getAdvertisementList() {
        return this.advertisementService.getAdvertisementList();
    }
}
