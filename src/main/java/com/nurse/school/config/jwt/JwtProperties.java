package com.nurse.school.config.jwt;

public interface JwtProperties {
    String SECRET = "ukha";
    int EXPIRATION_TIME = 600000*10; // 100분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
