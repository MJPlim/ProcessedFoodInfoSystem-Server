package com.plim.plimserver.global.domain.mail.service;

import com.plim.plimserver.domain.user.exception.EmailDuplicateException;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.domain.mail.domain.EmailAuthCode;
import com.plim.plimserver.global.domain.mail.domain.EmailSubject;
import com.plim.plimserver.global.domain.mail.repository.EmailAuthCodeRepository;
import com.plim.plimserver.global.domain.mail.util.EmailAuthCodeGenerator;
import com.plim.plimserver.global.domain.mail.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class EmailAuthService {

    private final EmailUtil emailUtil;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendAuthCode(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailDuplicateException(user.getEmail());
        });
        EmailAuthCodeGenerator generator = new EmailAuthCodeGenerator();
        String authCode = generator.generateAuthCode();
        emailAuthCodeRepository.findByEmail(email).ifPresent(EmailAuthCode::checkAuthCodeRequestTime);
        EmailAuthCode emailAuthCode = emailAuthCodeRepository.findByEmail(email)
                .orElseGet(() -> {
                    return emailAuthCodeRepository.save(EmailAuthCode.builder()
                            .email(email)
                            .build());
                });
        emailAuthCode.setAuthCode(authCode);
        emailUtil.sendEmail(email, EmailSubject.EMAIL_AUTH_CODE_REQUEST, authCode);
    }

}
