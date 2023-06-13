package com.nurse.school.entity;

import javax.persistence.*;

/**
 * 보건일지 처치
 */

@Entity
@Table(name = "health_document_aid")
public class Aid {

    @Id @GeneratedValue
    @Column(name = "doc_aid_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "document_id")
    private Main main;

    @OneToOne(mappedBy = "aid") // 일대일 양방향
    private Symp symp;

    private String aid;
    private String aid_detail;

}
