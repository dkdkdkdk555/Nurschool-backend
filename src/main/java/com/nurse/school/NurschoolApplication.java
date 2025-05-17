package com.nurse.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebSecurity
public class NurschoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(NurschoolApplication.class, args);
	}

//	@Bean ---> CORS 설정인데 SecurityConfig 설정에 이어서 막아둠
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
////				registry.addMapping("/**").allowedOrigins("http://front-server.com");
//			}
//		};
//	}
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
