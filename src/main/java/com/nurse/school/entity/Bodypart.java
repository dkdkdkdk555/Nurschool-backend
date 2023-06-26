package com.nurse.school.entity;

import javax.persistence.*;

/**
 * 계통 분류
 */
@Entity
@Table(name = "body_system")
public class Bodypart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_id")
    private Long id;

    private String system_name; // 계통명 (호흡기계, 순환계 등 ..)
    private String system_part; // 부위
}
