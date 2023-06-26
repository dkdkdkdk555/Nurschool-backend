package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;
import com.nurse.school.entity.common.PaymentStatus;
import com.nurse.school.entity.common.Persontype;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 인원정보
 */
@Entity
@Table(name = "person_info")
public class Person {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "workspace_id")
    private School school; // FK

    private String permanent_id; // 학생고유번호

    @Column(name = "person_name")
    private String name; // 학생이름

    @Column(name = "person_gender")
    private String gender; // 성별

    @Column(name = "person_birthday")
    private LocalDate birthday; // 생년월일

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type")
    private Persontype persontype; // 학생(STUDENT) or 교직원(STAFF)

    private Boolean patient_yn; // 요양호자 여부

    private int grade; // 학년

    @Column(name = "class")
    private String clss; // 반

    private int class_id; // 반에서 번호

    @Embedded
    private Audit auditInfo;


}
