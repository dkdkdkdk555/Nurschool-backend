package com.nurse.school.controller;

import com.nurse.school.entity.School;
import com.nurse.school.entity.User;
import com.nurse.school.entity.common.AuthStatus;
import com.nurse.school.entity.common.PaymentStatus;
import com.nurse.school.service.SchoolService;
import com.nurse.school.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InitUser {

    private final SchoolService schoolService;
    private final UserService userService;

    @PostConstruct
    public void init(){ // 데이터 임의 생성
        School school = new School("당진고등학교", "1114-1234", "041-355-3556", "충청남도 당진시 채운동");
        Long id = schoolService.join(school);

        School school1 = schoolService.findOne(id).get();

        User user = new User(school1, "박욱현", "dkdkdkdk555@naver.com", "ss2022", PaymentStatus.Y,
                "010-2868-8557", "Y", AuthStatus.Y, 0);

        userService.join(user);

    }
}