package com.plim.plimserver.domain.favorite.api;

import com.plim.plimserver.domain.favorite.domain.Favorite;
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

    @PostMapping("/add")
    public boolean addFavoriteFood(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam(name = "foodId") Long foodId) {
        try {
            return this.favoriteService.addFavoriteFood(principalDetails, foodId);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @GetMapping("/list")
    public List<FavoriteResponse> getFavoriteList(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return this.favoriteService.getFavoriteFoodList(principalDetails);
    }

}
