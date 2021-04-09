package com.plim.plimserver.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.post.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>{

}
