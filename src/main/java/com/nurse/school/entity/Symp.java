package com.nurse.school.entity;

import javax.persistence.*;

/**
 * 보건일지 증상
 */
@Entity
@Table(name = "health_document_symp")
public class Symp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_symp_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "document_id")
    private Main main; // FK

    @OneToOne // 일대일 양방향
    @JoinColumn(name = "doc_aid_id")
    private Aid aid; // FK

    private String symptoms;
    private String body_part;
    private String symptoms_detail; //  varchar(200)

}
