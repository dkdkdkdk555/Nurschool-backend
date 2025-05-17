package com.nurse.school.config.jwt;


import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurse.school.config.auth.PrincipalDetails;
import com.nurse.school.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import com.auth0.jwt.JWT;

/**
 * 스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있음
 * /login 요청해서 username, password 전송하면 (POST)
 * UsernamePasswordAuthenticationFilter 동작을 함,,
 * 근데 formlogin().disable() 처리를 해놔서 동작을 안함 이거를 다시 동작시켜야
 * 그럴려면
 *  요 필터를 SecurityConfig 에서 시큐리티 필터체인에 등록!!!!
 *
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1. id, pw 받아서
        try {
            // json 으로 요청시
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            // 토큰 만들기
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword());
            // PrincipalDetailsService의 loadUserByUsername() 이 실행된 후 정상이면 Authentication이 리턴됨
            // DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken); // 매니져가 인증을해서 Authentication 객체를 만들어줌

//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//            System.out.println(principalDetails.getUser().getUsername()); // 이게 조회가 된다는건 로그인 됫다는뜻
            // authentication 객체가 Security session 영역에 저장을 해야하고 그방법이 return
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 처리 중 오류 발생", e);
        }
    }

    // attemptAutentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됨
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        try {
            RSAPrivateKey privateKey = RSAKeyUtil.getPrivateKey(JwtProperties.PRIVATE_KEY_PATH);
            String jwtToken = JWT.create()
                    .withSubject("욱하토큰")
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                    .withClaim("id", principalDetails.getUser().getLoginId())
                    .withClaim("loginId", principalDetails.getUser().getLoginId())
                    .sign(Algorithm.RSA256(null, privateKey));
            response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        } catch (Exception e){
            throw new RuntimeException("JWT 생성 실패", e);
        }
    }
}
