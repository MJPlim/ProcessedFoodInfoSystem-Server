package com.plim.plimserver.global.domain.mail.repository;

import com.plim.plimserver.global.domain.mail.domain.EmailAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthCodeRepository extends JpaRepository<EmailAuthCode, Long> {

    Optional<EmailAuthCode> findByEmail(String email);

}
