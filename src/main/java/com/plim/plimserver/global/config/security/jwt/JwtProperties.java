package com.plim.plimserver.global.config.security.jwt;

public interface JwtProperties {
    String SECRET_KEY = "PLIMSERVERAWESOME";
    int EXPIRATION_TIME = 1000 * 60 * 60;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
