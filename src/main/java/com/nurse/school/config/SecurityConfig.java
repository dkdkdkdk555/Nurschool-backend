package com.nurse.school.config;


import com.nurse.school.config.jwt.JwtAuthenticationFilter;
import com.nurse.school.config.jwt.JwtAuthorizationFilter;
import com.nurse.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 않겠다.
                .and()
                .addFilter(corsFilter) // 크로스 오리진 무시하고 모든 요청 받는다.
                .formLogin().disable() // 폼로그인 안쓴다. --> /login 이거 자동으로 동작안함
                .httpBasic().disable() // httpbasic 방식 안쓴다.
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // 로그인진행하는 필터기 때문에 AuthenticationManager 던져줘야함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
        /**
         * ROLE_USER ⇒ 유저 권한
         *   - 이제 막 회원가입 했을때
         * ROLE_MANAGER ⇒ 보건일지 관리자 권한
         *   - 회원가입 후 워크스페이스를 생성 하거나워크스페이스에서 이용이 허가된 상태
         * ROLE_ADMIN ⇒  시스템 관리자 권한
         *   - 모든 워크스페이스(보건일지)와 모든 학교 정보에 접근할 수 있는 권한
         */
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // 로그인 한 사용자의 인증객체를 가져온다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            // 이름을 가져와 @EntityListeners(AuditingEntityListener.class) 가 선언된 클래스에서 사용할 수 있게 해줌.
            return Optional.of(authentication.getName());
        };
    }
}
