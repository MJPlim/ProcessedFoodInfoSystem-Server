package com.plim.plimserver.domain.advertisement.service;

import com.plim.plimserver.domain.advertisement.domain.AdvertisementFood;
import com.plim.plimserver.domain.advertisement.dto.AdvertisementResponse;
import com.plim.plimserver.domain.advertisement.exception.AdvertisementExceptionMessage;
import com.plim.plimserver.domain.advertisement.exception.NoAdvertisementFoodDetailException;
import com.plim.plimserver.domain.advertisement.repository.AdvertisementRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.dto.FoodDetailResponse;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
    private final AdvertisementRepository advertisementRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, FoodRepository foodRepository) {
        this.advertisementRepository = advertisementRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public ArrayList<AdvertisementResponse> getAdvertisementFoodList() {
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
            advertisementFood.setImpressionCount(advertisementFood.getImpressionCount()+1);
        }
        return ads;
    }

    @Transactional
    @Override
    public FoodDetailResponse getFoodDetailForAdvertisement(Long adId) {
        Optional<AdvertisementFood> optionalAdvertisement = this.advertisementRepository.findById(adId);
        AdvertisementFood advertisementFood = optionalAdvertisement.orElseThrow(
                () -> new NoAdvertisementFoodDetailException(AdvertisementExceptionMessage.NO_ADVERTISEMENT_FOOD_DETAIL_EXCEPTION_MESSAGE));
        advertisementFood.setViewCount(advertisementFood.getViewCount()+1);
        advertisementFood.getFood().setViewCount(advertisementFood.getFood().getViewCount()+1);
        return FoodDetailResponse.builder()
                                 .foodId(advertisementFood.getFood().getId())
                                 .foodName(advertisementFood.getFood().getFoodName())
                                 .category(advertisementFood.getFood().getCategory())
                                 .manufacturerName(advertisementFood.getFood().getManufacturerName())
                                 .foodImageAddress(advertisementFood.getFood().getFoodImage().getFoodImageAddress())
                                 .foodMeteImageAddress(advertisementFood.getFood().getFoodImage().getFoodMeteImageAddress())
                                 .materials(advertisementFood.getFood().getFoodDetail().getMaterials())
                                 .nutrient(advertisementFood.getFood().getFoodDetail().getNutrient())
                                 .allergyMaterials(advertisementFood.getFood().getAllergyMaterials())
                                 .viewCount(advertisementFood.getFood().getViewCount())
                                 .reviewList(advertisementFood.getFood().getReviewList())
                                 .favoriteList(advertisementFood.getFood().getFavoriteList())
                                 .build();
    }

    @Override
    public boolean selectAdvertisement(Long id1, Long id2, Long id3) throws NoSuchElementException{
        this.setAdvertisementFood(id1);
        if (id2 != null) {
            this.setAdvertisementFood(id2);
        }
        if (id3 != null) {
            this.setAdvertisementFood(id3);
        }

        return true;
    }

    private void setAdvertisementFood(Long id) throws NoSuchElementException {
        Optional<Food> optFood = this.foodRepository.findById(id);
        Food food = optFood.orElseThrow(NoSuchElementException::new);
        Optional<AdvertisementFood> optionalFindFood = this.advertisementRepository.findByFood(food);
        if (!optionalFindFood.isPresent()) {
            this.advertisementRepository.save(AdvertisementFood.builder().food(food).adState("on").build());
        }
    }
}