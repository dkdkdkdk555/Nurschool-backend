package com.nurse.school.dto;

import lombok.Data;

/**
 * 회원가입 DTO
 */
@Data
public class JoinDto {
    private String id;
    private String pw;
    private String name;
    private String tel_no;
    private String school_name;
    private String school_biz_num;
    private String sign_terms_yn;
    private String ad_terms_yn;
    private String school_tel;
    private String school_addr;
}
