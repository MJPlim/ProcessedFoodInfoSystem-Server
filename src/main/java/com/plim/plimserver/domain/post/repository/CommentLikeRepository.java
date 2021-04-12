package com.plim.plimserver.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.post.domain.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>{

}
