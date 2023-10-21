package com.nurse.school.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 보건일지 처치
 */

@Entity
@Table(name = "health_document_aid")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Aid {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_aid_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name="doc_symp_id")
    private Symp symp;

    private String aid;
    private String aid_detail;

}
