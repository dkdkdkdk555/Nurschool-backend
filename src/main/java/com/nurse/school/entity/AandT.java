package com.nurse.school.entity;

import javax.persistence.*;

/**
 * 처치
 */
@Entity
@Table(name = "aid_and_treatment")
public class AandT {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "user_id")
    private User user; // FK, 커스텀 처치를 부여하는 교사 id

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "system_id")
    private Bodypart bodypart; // 계통분류

    private String aid_type; // 유형(약처방/응급조치/침대이용 등)
    private String aid_name; // 처치명
    private String aid_detail; // 처치 상세설명


}
