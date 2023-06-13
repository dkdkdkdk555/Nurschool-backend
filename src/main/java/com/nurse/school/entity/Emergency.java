package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;

import javax.persistence.*;

/**
 *  응급일지
 */
@Entity
@Table(name = "health_document_emergency")
public class Emergency {

    @Id
    @GeneratedValue
    @Column(name = "emergency_id")
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
