package com.plim.plimserver.global.domain.mail.api;

import com.plim.plimserver.global.domain.mail.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @GetMapping("email-auth")
    public void emailAuth(HttpServletResponse response, String email, String authCode) throws IOException {
        emailAuthService.emailValidate(response, email, authCode);
        response.sendRedirect("https://www.google.com");
    }

}
