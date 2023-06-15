package com.nurse.school.configuration;

import com.nurse.school.entity.User;
import com.nurse.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("이름이름  : "+ authentication.getName().toString());
        User user = userService.findOneByLoginId(authentication.getName().toString());
        if(user==null) throw new BadCredentialsException("Not Found User");
        String reqPassword = authentication.getCredentials().toString();user.getPassword();
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if(!passwordEncoder.matches(reqPassword, user.getPassword())) throw new BadCredentialsException("Not Found User");
        if(!user.getPassword().equals(reqPassword)) throw new BadCredentialsException("Not Match Password");

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
