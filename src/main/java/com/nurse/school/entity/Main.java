package com.nurse.school.entity;

import com.nurse.school.entity.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 보건일지
 */
@Entity
@Table(name = "health_document_main")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Main extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private School school;


    private LocalDateTime visit_time; // 방문일시

    private String memo; // 특이사항 등 메모

    @Builder
    public Main(Person person, School school, LocalDateTime visit_time, String memo) {
        this.person = person;
        this.school = school;
        this.visit_time = visit_time;
        this.memo = memo;
    }
}
