package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;
import com.nurse.school.entity.common.PaymentStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * 보건교사 정보
 */
@Entity
@Getter
@Table(name = "user_info")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String user_tel; // 연락처

    private String sign_terms_yn; // 약관동의 여부, 기본 Y

    private String roles; // 권한 ROLE_USER + workspace_id 이런식으로 생성

    private int login_fail_count; // 로그인 실패 횟수

    @Embedded
    private Audit auditInfo;


    public List<String> getRoleList(){ // 역할이 하나 계정당 두개 이상 있는경우..
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
