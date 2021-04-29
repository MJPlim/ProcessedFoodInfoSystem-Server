package com.plim.plimserver.domain.advertisement.service;

import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;

import java.util.ArrayList;

public interface AdvertisementService {
    ArrayList<AdvertisementResponse> getAdvertisementList();
}
