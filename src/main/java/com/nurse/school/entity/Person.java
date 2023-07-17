package com.nurse.school.entity;

import com.nurse.school.entity.common.BaseEntity;
import com.nurse.school.entity.common.Persontype;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 인원정보
 */
@Entity
@Getter
@Table(name = "person_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person extends BaseEntity{

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
    private String gender; // 성별 남성-여성

    @Column(name = "person_birthday")
    private LocalDate birthday; // 생년월일

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type")
    private Persontype persontype; // 학생(STUDENT) or 교직원(STAFF)

    private String patient_yn; // 요양호자 여부

    private String grade; // 학년

    @Column(name = "class")
    private String clss; // 반

    private int class_id; // 반에서 번호

    @Builder
    public Person(School school, String permanent_id, String name, String gender,
                  LocalDate birthday, Persontype persontype, String patient_yn,
                  String grade, String clss, int class_id) {
        this.school = school;
        this.permanent_id = permanent_id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.persontype = persontype;
        this.patient_yn = patient_yn;
        this.grade = grade;
        this.clss = clss;
        this.class_id = class_id;
    }
}
