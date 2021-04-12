package com.plim.plimserver.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.favorite.domain.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
}
