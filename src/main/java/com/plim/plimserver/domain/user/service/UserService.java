package com.plim.plimserver.domain.user.service;

import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.dto.FindPasswordRequest;
import com.plim.plimserver.domain.user.dto.SignUpUserRequest;
import com.plim.plimserver.domain.user.exception.EmailDuplicateException;
import com.plim.plimserver.domain.user.exception.EmailNotVerifedException;
import com.plim.plimserver.domain.user.exception.PasswordMismatchException;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import com.plim.plimserver.global.domain.mail.domain.EmailAuthCode;
import com.plim.plimserver.global.domain.mail.domain.EmailSubject;
import com.plim.plimserver.global.domain.mail.repository.EmailAuthCodeRepository;
import com.plim.plimserver.global.domain.mail.util.EmailAuthCodeGenerator;
import com.plim.plimserver.global.domain.mail.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final EmailUtil emailUtil;

    public User saveUser(SignUpUserRequest dto) {
        if (emailAuthCodeRepository.existsByEmail(dto.getEmail()))
            throw new EmailNotVerifedException(UserExceptionMessage.EMAIL_NOT_VERIFIED_EXCEPTION_MESSAGE);
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException(UserExceptionMessage.EMAIL_DUPLICATE_EXCEPTION_MESSAGE);

        EmailAuthCodeGenerator authCodeGenerator = new EmailAuthCodeGenerator();
        String authCode = authCodeGenerator.generateAuthCode();
        emailAuthCodeRepository.save(EmailAuthCode.builder()
                .email(dto.getEmail())
                .authCode(authCode)
                .build());

        String message = emailUtil.getEmailAuthMessage(dto.getEmail(), authCode);
        emailUtil.sendEmail(dto.getEmail(), EmailSubject.EMAIL_AUTH_REQUEST, message);

        return userRepository.save(dto.toEntity(passwordEncoder));
    }

    @Transactional
    public User withdraw(PrincipalDetails principal, String password) {
        User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches)
            throw new PasswordMismatchException(UserExceptionMessage.PASSWORD_MISMATCH_EXCEPTION_MESSAGE);

        user.withdraw();
        return user;
    }

    @Transactional
    public User findPassword(FindPasswordRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        EmailAuthCodeGenerator authCodeGenerator = new EmailAuthCodeGenerator();
        String authCode = authCodeGenerator.generateAuthCode();
        user.updatePassword(passwordEncoder.encode(authCode));

        String message = emailUtil.getFindPasswordMessage(dto.getEmail(), authCode);
        emailUtil.sendEmail(dto.getEmail(), EmailSubject.FIND_PASSWORD_REQUEST, message);

        return user;
    }
}
