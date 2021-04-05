package com.plim.plimserver.domain.user.repository;

import com.plim.plimserver.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
