package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;
import com.nurse.school.entity.common.PaymentStatus;
import lombok.Getter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

/**
 * 학교정보 엔티티
 */
@Entity
@Table(name = "school_info")
@Getter
public class School {

    @Id @GeneratedValue
    @Column(name = "workspace_id")
    private Long id; // PK

    @Column(name = "school_name")
    private String name; // 학교명

    @Column(name = "school_biz_num")
    private String bizNum; // 사업자번호

    @Column(name = "school_tel")
    private String schoolTel; //  학교 연락처

    @Column(name = "school_address")
    private String schoolAddr; // 학교 주소

    @Embedded
    private Audit auditInfo;

    public School(String name, String bizNum, String schoolTel, String schoolAddr) {
        this.name = name;
        this.bizNum = bizNum;
        this.schoolTel = schoolTel;
        this.schoolAddr = schoolAddr;
    }

    protected School() {

    }
}
