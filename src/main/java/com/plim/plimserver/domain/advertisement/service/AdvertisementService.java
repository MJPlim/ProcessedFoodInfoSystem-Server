package com.plim.plimserver.domain.advertisement.service;

import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface AdvertisementService {
    ArrayList<AdvertisementResponse> getAdvertisementFoodList();

    boolean selectAdvertisement(Long id1, Long id2, Long id3) throws NoSuchElementException;

    FoodDetailResponse getFoodDetailForAdvertisement(Long adId);
}
