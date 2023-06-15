package com.nurse.school.controller;


import com.nurse.school.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/api/login/success")
    public Result<loginResponse> loginSuccess(){
        System.out.println("로그인 성공");
        return new Result<>(new loginResponse("success"));
    }

    @GetMapping("/api/login/fail")
    public Result<loginResponse> loginfail(){
        System.out.println("로그인 실패");
        return new Result<>(new loginResponse("failure"));
    }

    @GetMapping("/user")
    public String moveUser(){
        return "nurse";
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class loginResponse {
        private String result;
    }
}
