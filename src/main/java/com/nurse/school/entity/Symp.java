package com.nurse.school.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 보건일지 증상
 */
@Entity
@Table(name = "health_document_symp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Symp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_symp_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "document_id")
    private Main main; // FK

    private String symptoms;
    private String body_part; // 일대다 단방향 연관관계 매핑은 복잡도 문제로 사용하지 않는다.
    private String symptoms_detail; //  varchar(200)


    @Builder
    public Symp(Main main, String symptoms, String body_part, String symptoms_detail) {
        this.main = main;
        this.symptoms = symptoms;
        this.body_part = body_part;
        this.symptoms_detail = symptoms_detail;
    }
}
