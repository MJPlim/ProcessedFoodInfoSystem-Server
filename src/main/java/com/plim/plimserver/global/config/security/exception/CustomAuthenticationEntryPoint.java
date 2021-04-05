package com.plim.plimserver.global.config.security.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");

        if (httpServletRequest.getAttribute("exception").equals(ErrorCode.WITHDREW))
            httpServletResponse.getWriter().println("{\n" +
                    "\t“error-message”: “해당 게정은 탈퇴된 계정입니다.”\n" +
                    "}");
    }

}
