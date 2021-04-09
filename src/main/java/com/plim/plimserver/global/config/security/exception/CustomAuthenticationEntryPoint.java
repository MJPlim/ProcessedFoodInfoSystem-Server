package com.plim.plimserver.global.config.security.exception;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map<String, String> error = new HashMap<>();
        Gson gson = new Gson();
        if (httpServletRequest.getAttribute("exception").equals(ErrorCode.WITHDREW)) {
            error.put("error-message", "해당 계정은 탈퇴된 계정입니다.");
            String message = gson.toJson(error);
            httpServletResponse.getWriter().println(message);
        }
    }

}