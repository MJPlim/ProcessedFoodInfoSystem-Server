package com.plim.plimserver.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plim.plimserver.domain.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
}
