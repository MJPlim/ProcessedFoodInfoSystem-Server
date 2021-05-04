package com.plim.plimserver.domain.favorite.service;

import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import java.util.NoSuchElementException;

public interface FavoriteService {
    boolean addFavoriteFood(PrincipalDetails principalDetails, Long foodId) throws NoSuchElementException;
}
