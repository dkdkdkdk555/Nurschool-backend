package com.nurse.school.entity;

import com.nurse.school.entity.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 보건교사 정보
 */
@Entity
@Getter
@Table(name = "user_info")
public class User extends BaseEntity { // 23.6.27.화 _ ERD 반영

    public User() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST) // 다대일 단방향, 저장 시 만 영속성전이 설정 (로그인하는데 제 까지 조회됨)
    @JoinColumn(name = "workspace_id")
    private School school;

    @Column(name = "user_name")
    private String name; // 사용자 이름

    @Column(name = "login_id")
    private String loginId; // 아이디

    private String password; // 비번

    @Column(name = "payment_yn")
    private String payYn; // 결제 여부

    private String user_tel; // 연락처

    private String sign_terms_yn; // 필수 약관동의 여부

    private String ad_terms_yn; // 광고성 약관 동의 여부

    @Setter
    private String roles; // 권한 ROLE_USER + workspace_id 이런식으로 생성

    private int login_fail_count; // 로그인 실패 횟수

    public List<String> getRoleList(){ // 역할이 하나 계정당 두개 이상 있는경우..
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public User(School school, String name, String loginId, String password,
                String payYn, String user_tel, String sign_terms_yn,
                String ad_terms_yn, String roles, int login_fail_count) {
        this.school = school;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.payYn = payYn;
        this.user_tel = user_tel;
        this.sign_terms_yn = sign_terms_yn;
        this.ad_terms_yn = ad_terms_yn;
        this.roles = roles;
        this.login_fail_count = login_fail_count;
    }
}
