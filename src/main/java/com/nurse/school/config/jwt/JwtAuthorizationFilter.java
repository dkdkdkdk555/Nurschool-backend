package com.nurse.school.config.jwt;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nurse.school.config.auth.PrincipalDetails;
import com.nurse.school.entity.User;
import com.nurse.school.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import com.auth0.jwt.JWT;


/**
 * 시큐리티가 filter를 가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 잇음
 * 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음
 * 만약, 권한이나 인증이 필요한 주소가 아니라면 위 필터를 안탐
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // 1. 토큰 존재 여부 확인
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. 토큰 추출
        String token = header.replace(JwtProperties.TOKEN_PREFIX, "");

        try {
            // 3. 공개키 로딩
            RSAPublicKey publicKey = RSAKeyUtil.getPublicKey(JwtProperties.PUBLIC_KEY_PATH);

            // 4. 검증 및 payload 추출
            JWTVerifier verifier = JWT.require(Algorithm.RSA256(publicKey, null)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String loginId = decodedJWT.getClaim("loginId").asString();

            // 5. 사용자 조회 및 인증객체 생성
            if (loginId != null) {
                User user = userRepository.findUserByLoginId(loginId);
                PrincipalDetails principalDetails = new PrincipalDetails(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        principalDetails, null, principalDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            throw new RuntimeException("JWT 검증 실패", e);
        }

        // 6. 다음 필터로 진행
        chain.doFilter(request, response);
    }

}
