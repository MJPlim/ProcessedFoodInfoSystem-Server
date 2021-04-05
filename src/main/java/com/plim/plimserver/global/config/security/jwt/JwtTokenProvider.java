package com.plim.plimserver.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;

    public String createToken(String username) {
        return JWT.create()
                .withSubject("jwt token")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("username", username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET_KEY));
    }

    public String validateToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(JwtProperties.TOKEN_PREFIX))
            return "";

        String username = getUsername(authorization);
        if (username != null) {
            PrincipalDetails principalDetails = new PrincipalDetails(userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 패스워드를 확인해주세요.")));
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return username;
    }

    private String getUsername(String authorization) {
        String jwtToken = authorization.replace(JwtProperties.TOKEN_PREFIX, "");
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET_KEY)).build()
                .verify(jwtToken)
                .getClaim("username")
                .asString();
    }

}
