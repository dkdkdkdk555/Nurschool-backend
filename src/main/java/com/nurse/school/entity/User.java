package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;
import com.nurse.school.entity.common.AuthStatus;
import com.nurse.school.entity.common.PaymentStatus;
import lombok.Getter;

import javax.persistence.*;


/**
 * 보건교사 정보
 */
@Entity
@Getter
@Table(name = "user_info")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "workspace_id")
    private School school;

    @Column(name = "user_name")
    private String name; // 사용자 이름

    @Column(name = "login_id")
    private String loginId; // 아이디

    private String password; // 비번

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_yn")
    private PaymentStatus payYn; // 결제 여부

    @Column(name = "user_tel")
    private String tel_no; // 연락처

    private String sign_terms_yn; // 약관동의 여부, 기본 Y

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_yn")
    private AuthStatus authYn; // 권한 승인 여부

    private int login_fail_count; // 로그인 실패 횟수

    @Embedded
    private Audit auditInfo;

    public User(School school, String name, String loginId, String password, PaymentStatus payYn, String tel_no, String sign_terms_yn, AuthStatus authYn, int login_fail_count) {
        this.school = school;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.payYn = payYn;
        this.tel_no = tel_no;
        this.sign_terms_yn = sign_terms_yn;
        this.authYn = authYn;
        this.login_fail_count = login_fail_count;
    }
}
