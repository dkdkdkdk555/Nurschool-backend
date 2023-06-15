package com.nurse.school.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class LoginDto {

    private String loginId;
    private String password;
}
