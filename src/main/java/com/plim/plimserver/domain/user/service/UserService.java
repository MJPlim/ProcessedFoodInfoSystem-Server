package com.plim.plimserver.domain.user.service;

import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.dto.SignUpUserRequest;
import com.plim.plimserver.domain.user.exception.EmailDuplicateException;
import com.plim.plimserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User saveUser(SignUpUserRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException(dto.getEmail());

        return userRepository.save(dto.toEntity(passwordEncoder));
    }

}
