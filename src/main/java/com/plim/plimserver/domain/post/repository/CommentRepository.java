package com.plim.plimserver.domain.post.repository;

import com.plim.plimserver.domain.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countAllByUserId(Long userId);

}
