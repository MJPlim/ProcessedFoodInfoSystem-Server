package com.plim.plimserver.domain.favorite.api;

import com.plim.plimserver.domain.favorite.dto.FavoriteResponse;
import com.plim.plimserver.domain.favorite.service.FavoriteService;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping("/list")
    public List<FavoriteResponse> getFavoriteList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return this.favoriteService.getFavoriteFoodList(principalDetails);
    }

    @PostMapping("/addFavorite")
    public boolean addFavoriteFood(@AuthenticationPrincipal PrincipalDetails principalDetails
            , @RequestParam(name = "foodId") Long foodId) {
        try {
            return this.favoriteService.addFavoriteFood(principalDetails, foodId);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @DeleteMapping("/deleteFavorite")
    public void deleteFavoriteFood(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam(name = "foodId") Long foodId) {
        this.favoriteService.deleteFavoriteFood(principalDetails, foodId);
    }

    @GetMapping("/checkFavorite")
    public boolean getFavoriteStateForSpecificFood(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam(name = "foodId") Long foodId) {
        return this.favoriteService.getFavoriteStateForSpecificFood(principalDetails, foodId);
    }

}
