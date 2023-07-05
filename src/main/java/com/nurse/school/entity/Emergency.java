package com.nurse.school.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *  응급일지
 */
@Entity
@Table(name = "health_document_emergency")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emergency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emergency_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private School school;

    
    
}
