package com.nurse.school.config.auth;


import com.nurse.school.entity.User;
import com.nurse.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *  http://localhost:8080/login 요청 올때 동작
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User userEntity = userRepository.findUserByLoginId(loginId);
        return new PrincipalDetails(userEntity);
    }
}
