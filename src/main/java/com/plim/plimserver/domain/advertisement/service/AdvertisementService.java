package com.plim.plimserver.domain.advertisement.service;

import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface AdvertisementService {
    ArrayList<AdvertisementResponse> getAdvertisementFoodList();

    boolean selectAdvertisement(Long id1, Long id2, Long id3) throws NoSuchElementException;
}
