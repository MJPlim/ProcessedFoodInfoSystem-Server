package com.plim.plimserver.domain.favorite.repository;

import com.plim.plimserver.domain.favorite.domain.Favorite;
import com.plim.plimserver.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

    List<Favorite> findAllByUser(User user);

}
