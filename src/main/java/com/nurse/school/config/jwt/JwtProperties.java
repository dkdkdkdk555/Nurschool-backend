package com.nurse.school.config.jwt;

public class JwtProperties {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 180000*10; // 30분

    // RSA 키 경로
    public static final String PUBLIC_KEY_PATH = "src/main/resources/rsa/public.pem";
    public static final String PRIVATE_KEY_PATH = "src/main/resources/rsa/private.pem";
}
