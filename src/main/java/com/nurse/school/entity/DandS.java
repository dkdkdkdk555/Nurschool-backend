package com.nurse.school.entity;

import javax.persistence.*;

/**
 * 질병/증상
 */
@Entity
@Table(name = "disease_and_symptom")
public class DandS {

    @Id @GeneratedValue
    @Column(name = "ds_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "user_id")
    private User user; // FK, 커스텀 질병/증상 부여하는 교사 id

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "system_id")
    private Bodypart bodypart; // 계통분류

    private String ds_type; // 증상/질병 유형
    private String ds_name; // 증상명/질병명
    private String ds_detail; // 증상 상세설명
}
