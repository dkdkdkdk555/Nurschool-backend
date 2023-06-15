package com.nurse.school.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 요청자의 의도치않은 서버 공격을 방지하는 token 검증 옵션
        http.csrf().disable(); //  단순 로그인 구현이 목적이므로 disable

        // authorizeRequests() : 경로에 권한, 인증을 설정한다는 선언
        http.authorizeHttpRequests((requests) ->
                requests.antMatchers("/", "/home", "/api/login/fail").permitAll() // 인증이 필요없는 페이지 지정 , ("/경로/**") 이런식으로 하위경로 전체 지정도 가능
                        .anyRequest().authenticated()
        );

        http.formLogin().loginPage("/login") // Form 로그인을 선언, 요청시 Body, form-data 타입으로 전송해야함
                .loginProcessingUrl("/api/login")
                .usernameParameter("loginId").passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler);

        http.sessionManagement().maximumSessions(1) // 최대 허용 가능 세션 수 설정 ( -1은 로그인 개수 무한대)
                .maxSessionsPreventsLogin(true); // 동시로그인 설정, true면 동시로그인 불ㅠ
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
