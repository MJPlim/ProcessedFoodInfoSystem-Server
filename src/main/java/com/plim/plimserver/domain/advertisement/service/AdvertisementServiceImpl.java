package com.plim.plimserver.domain.advertisement.service;

import com.plim.plimserver.domain.advertisement.domain.AdvertisementFood;
import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;
import com.plim.plimserver.domain.advertisement.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public ArrayList<AdvertisementResponse> getAdvertisementList() {
        ArrayList<AdvertisementResponse> ads = new ArrayList<>();
        List<AdvertisementFood> onAds = this.advertisementRepository.findAllByAdState("on");
        Random r = new Random();
        Set<Integer> numSet = new HashSet<>();
        boolean hasThree = false;
        while (!hasThree) {
            if (numSet.size() == 3) {
                hasThree = true;
            }
            int id = r.nextInt(onAds.size());
            numSet.add(id);
        }

        for (int i : numSet) {
            AdvertisementFood advertisementFood = onAds.get(i);
            AdvertisementResponse response = AdvertisementResponse.builder()
                                                                  .id(advertisementFood.getId())
                                                                  .food(advertisementFood.getFood())
                                                                  .build();
            ads.add(response);
        }
        return ads;
    }
}
