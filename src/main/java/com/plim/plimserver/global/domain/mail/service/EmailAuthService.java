package com.plim.plimserver.global.domain.mail.service;

import com.plim.plimserver.domain.user.domain.UserStateType;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.exception.EmailDuplicateException;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.domain.mail.domain.EmailAuthCode;
import com.plim.plimserver.global.domain.mail.domain.EmailSubject;
import com.plim.plimserver.global.domain.mail.repository.EmailAuthCodeRepository;
import com.plim.plimserver.global.domain.mail.util.EmailAuthCodeGenerator;
import com.plim.plimserver.global.domain.mail.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class EmailAuthService {

    private final EmailUtil emailUtil;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void emailValidate(HttpServletResponse response, String email, String authCode) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원가입 되지 않은 이메일 주소입니다."));
        if (user.getState().equals(UserStateType.NORMAL)) response.sendRedirect("https://www.google.com");
        EmailAuthCode emailAuthCode = emailAuthCodeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원가입 되지 않은 이메일 주소입니다."));
        if (!emailAuthCode.getAuthCode().equals(authCode)) throw new IllegalArgumentException("인증 번호가 일치하지 않습니다.");

        user.emailVerificationCompleted();
        emailAuthCodeRepository.delete(emailAuthCode);
    }

}
