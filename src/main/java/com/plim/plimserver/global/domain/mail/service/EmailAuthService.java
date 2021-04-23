package com.plim.plimserver.global.domain.mail.service;

import com.plim.plimserver.domain.user.domain.UserStateType;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.EmailNotFoundException;
import com.plim.plimserver.domain.user.exception.UserExceptionMessage;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.domain.mail.domain.EmailAuthCode;
import com.plim.plimserver.global.domain.mail.repository.EmailAuthCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class EmailAuthService {

    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void emailValidate(String email, String authCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(UserExceptionMessage.EMAIL_NOT_FOUND_EXCEPTION_MESSAGE));
        if (user.getState().equals(UserStateType.NORMAL)) return;
        EmailAuthCode emailAuthCode = emailAuthCodeRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(UserExceptionMessage.EMAIL_NOT_FOUND_EXCEPTION_MESSAGE));

        emailAuthCode.validateCode(authCode);
        user.emailVerificationCompleted();
        emailAuthCodeRepository.delete(emailAuthCode);
    }

}
