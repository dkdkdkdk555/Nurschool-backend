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
 * 비 유저 기능 컨트롤러
 */
@RestController
public class IndexController {

    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired UserService userService;

    // 회원가입
    @PostMapping("/api/v1/join")
    public String join(@RequestBody JoinDto joinDto){
        System.out.println(joinDto.toString());
        // 패스워드 인코딩
        joinDto.setPw(bCryptPasswordEncoder.encode(joinDto.getPw()));
        userService.join(joinDto);

        return "success";  // 리다이렉트
    }

    @PostMapping("/api/v1/biz_verify")
    public String bizNumVerify(String biznum){

        return "";
    }

    @GetMapping("/user/u1")
    public String user1(){

        return "유저 권한 확인";
    }

    @GetMapping("/admin/u1")
    public String admin1(){

        return "관리자 권한 확인";
    }

    @GetMapping("/manager/u1")
    public String manager1(){

        return "매니져 권한 확인";
    }
}
