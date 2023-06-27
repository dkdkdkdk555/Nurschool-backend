package com.nurse.school.entity;

import com.nurse.school.entity.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

/**
 * 학교정보 엔티티
 */
@Entity
@Table(name = "school_info")
@Getter
public class School extends BaseEntity { // 23.6.27.화 _ ERD 반영

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

    public School() {

    }
}
