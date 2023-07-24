package com.nurse.school.controller;

import com.nurse.school.dto.JoinDto;
import com.nurse.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 기능 컨트롤러
 */
@RestController
public class IndexController {

    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired UserService userService;

    // 회원가입
    @PostMapping("/api/v1/join")
    public String join(@RequestBody JoinDto joinDto){
        System.out.println(joinDto.toString());
        joinDto.setPw(bCryptPasswordEncoder.encode(joinDto.getPw()));
        userService.join(joinDto);
        return "success";  // 리다이렉트
    }
}
