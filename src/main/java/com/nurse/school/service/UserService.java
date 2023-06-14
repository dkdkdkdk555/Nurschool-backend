package com.nurse.school.service;


import com.nurse.school.entity.User;
import com.nurse.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    @Transactional(readOnly = false)
    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }
}
