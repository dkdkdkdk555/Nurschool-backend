package com.nurse.school.entity;

import com.nurse.school.entity.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 학교정보 엔티티
 */
@Entity
@Table(name = "school_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //서비스에서 생성자로 생성 막음 -> 메소드로 생성하자
public class School extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id")
    private Long id; // PK

    @Column(name = "school_name", nullable = false)
    private String name; // 학교명

    @Column(name = "school_biz_num", nullable = false)
    private String bizNum; // 사업자번호

    @Column(name = "school_tel")
    private String schoolTel; //  학교 연락처

    @Column(name = "school_address")
    private String schoolAddr; // 학교 주소

    @Builder
    public School(String name, String bizNum, String schoolTel, String schoolAddr) {
        this.name = name;
        this.bizNum = bizNum;
        this.schoolTel = schoolTel;
        this.schoolAddr = schoolAddr;
    }
}
