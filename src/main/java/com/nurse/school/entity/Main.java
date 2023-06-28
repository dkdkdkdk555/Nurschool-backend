package com.nurse.school.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 보건일지
 */
@Entity
@Table(name = "health_document_main")
public class Main {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private School school;

    private String permanent_id; // 학생고유번호

    private LocalDateTime visit_time; // 방문일시

    private String memo; // 특이사항 등 메모

    
    

}
