package com.plim.plimserver.domain.advertisement.repository;

import com.plim.plimserver.domain.advertisement.domain.AdvertisementFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<AdvertisementFood, Long> {
    List<AdvertisementFood> findAllByAdState(String state);
}
