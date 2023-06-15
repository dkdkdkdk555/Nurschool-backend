package com.nurse.school.service;


import com.nurse.school.dto.LoginDto;
import com.nurse.school.entity.User;
import com.nurse.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    //로그인
    public String login(LoginDto loginDto, HttpServletRequest request){
        // 회원검증
        User user = userRepository.findUserByLoginIdAndPassword(loginDto.getLoginId(), loginDto.getPassword());
        if(user == null){
            return "loginFail";
        }

        // 세션 생성
//        HttpSession session = request.getSession(true);
//        session.setAttribute("userId", user.getId());
//        session.setMaxInactiveInterval(180);

        return "succes";
    }

    public User findOneByLoginId(String loginId){
        return userRepository.findUserByLoginId(loginId);
    }

}
