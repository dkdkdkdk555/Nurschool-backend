package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;

import javax.persistence.*;

/**
 * 건강상담일지
 */

@Entity
@Table(name = "health_document_counsel")
public class Counsel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counsel_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private School school;

    @Embedded
    private Audit auditInfo;
}
