package com.plim.plimserver.domain.advertisement.repository;

import com.plim.plimserver.domain.advertisement.domain.AdvertisementFood;
import com.plim.plimserver.domain.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<AdvertisementFood, Long> {
    List<AdvertisementFood> findAllByAdState(String state);

    Optional<AdvertisementFood> findByFood(Food food);
}
