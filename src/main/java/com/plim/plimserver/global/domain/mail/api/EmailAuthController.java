package com.plim.plimserver.global.domain.mail.api;

import com.plim.plimserver.global.domain.mail.dto.EmailAuthCodeRequest;
import com.plim.plimserver.global.domain.mail.dto.EmailAuthCodeResponse;
import com.plim.plimserver.global.domain.mail.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @PostMapping("email-auth-code")
    public ResponseEntity<EmailAuthCodeResponse> emailAuthCodeSend(@Valid @RequestBody EmailAuthCodeRequest dto) {
        emailAuthService.sendAuthCode(dto.getEmail());
        return ResponseEntity.ok().body(EmailAuthCodeResponse.builder()
                .email(dto.getEmail())
                .message("해당 메일 주소로 인증 번호를 전송했습니다.")
                .build());
    }

    @GetMapping("email-auth")
    public void emailAuth(HttpServletResponse response, String email, String authCode) throws IOException {
        emailAuthService.validate(response, email, authCode);
        response.sendRedirect("https://www.google.com");
    }

}
