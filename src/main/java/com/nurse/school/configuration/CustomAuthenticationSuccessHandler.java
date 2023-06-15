package com.nurse.school.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("성공");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60); // 세션 최대 유효시간 (초단위)
//        response.sendRedirect("http://localhost:8080/api/login/success"); // 클라이언트는 리다이렉트 요청 받음
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Cookie cookie = new Cookie("nurschool", "ROLE_USER");
        cookie.setMaxAge(120);
        response.addCookie(cookie);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(session.getId());
        response.getWriter().write(body);

    }
}
