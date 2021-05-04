package com.plim.plimserver.domain.favorite.service;

import com.plim.plimserver.domain.favorite.domain.Favorite;
import com.plim.plimserver.domain.favorite.dto.FavoriteResponse;
import com.plim.plimserver.domain.favorite.repository.FavoriteRepository;
import com.plim.plimserver.domain.food.domain.Food;
import com.plim.plimserver.domain.food.dto.FoodResponse;
import com.plim.plimserver.domain.food.repository.FoodRepository;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FavoriteServiceImpl implements FavoriteService{
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public FavoriteServiceImpl(UserRepository userRepository, FavoriteRepository favoriteRepository, FoodRepository foodRepository) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    public boolean addFavoriteFood(PrincipalDetails principalDetails, Long foodId) throws NoSuchElementException{
        Food food = this.foodRepository.findById(foodId).orElseThrow(NoSuchElementException::new);
        User user = this.userRepository.findByEmail(principalDetails.getUsername())
                                       .orElseThrow(NoSuchElementException::new);

        this.favoriteRepository.save(Favorite.builder()
                                             .user(user)
                                             .food(food)
                                             .build());

        return true;
    }

    @Override
    public List<FavoriteResponse> getFavoriteFoodList(PrincipalDetails principalDetails) {
        User user = this.userRepository.findByEmail(principalDetails.getUsername())
                                       .orElseThrow(() ->
                                               new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        List<Favorite> favoriteList = this.favoriteRepository.findAllByUser(user);

        List<FavoriteResponse> responses = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            FavoriteResponse response = FavoriteResponse.builder()
                                                        .favoriteId(favorite.getId())
                                                        .food(FoodResponse.builder()
                                                                          .foodId(favorite.getFood().getId())
                                                                          .foodName(favorite.getFood().getFoodName())
                                                                          .category(favorite.getFood().getCategory())
                                                                          .manufacturerName(favorite.getFood().getManufacturerName())
                                                                          .foodImageAddress(favorite.getFood().getFoodImage().getFoodImageAddress())
                                                                          .foodMeteImageAddress(favorite.getFood().getFoodImage().getFoodMeteImageAddress())
                                                                          .build())
                                                        .build();
            responses.add(response);
        }
        return responses;
    }
}
