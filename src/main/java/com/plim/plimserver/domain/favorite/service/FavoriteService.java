package com.plim.plimserver.domain.favorite.service;

import com.plim.plimserver.domain.favorite.dto.FavoriteResponse;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import java.util.List;
import java.util.NoSuchElementException;

public interface FavoriteService {

    List<FavoriteResponse> getFavoriteFoodList(PrincipalDetails principalDetails);
    boolean addFavoriteFood(PrincipalDetails principalDetails, Long foodId) throws NoSuchElementException;
    void deleteFavoriteFood(PrincipalDetails principalDetails, Long foodId);
}
