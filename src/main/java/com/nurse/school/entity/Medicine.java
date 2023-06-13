package com.nurse.school.entity;

import com.nurse.school.entity.common.Audit;

import javax.persistence.*;

/**
 * 약품 등 재고
 */
@Entity
@Table(name = "medicine_stock")
public class Medicine {

    @Id @GeneratedValue
    @Column(name = "stock_id")
    private Long id;

    @ManyToOne //  다대일 단방향
    @JoinColumn(name = "workspace_id")
    private School school;

    @Column(name = "stock_name")
    private String name; // 재고명

    @Column(name = "stock_detail")
    private String detail; // 재고 설명

    @Column(name = "stock_quantity")
    private int stockQuantity; // 남은 재고 수

    @Embedded
    private Audit auditInfo;



}
