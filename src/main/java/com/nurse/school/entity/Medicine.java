package com.nurse.school.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.nurse.school.entity.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 약품 등 재고
 */
@Entity
@Getter
@Table(name = "medicine_stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Medicine extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @ManyToOne //  다대일 단방향
    @JoinColumn(name = "workspace_id")
    private School school;

    @Column(name = "stock_name")
    private String medicine_name; // 이름

    @Column(name = "stock_usage")
    private String usage; // 용도

    @Column(name = "stock_unit")
    private String unit; // 단위

    @Column(name = "stock_quantity")
    private int quantity; // 수량

    @Column(name = "stock_capa")
    private int capa; // 규격

    @Column(name = "stock_quantity_x_capa")
    private int capaXquantity; // 수량

//    @Column(name = "stock_side_effect")
//    private String sideEffect; // 부작용

    @Builder
    public Medicine(School school, String medicine_name, String usage, String unit,
                    int quantity, int capa, int capaXquantity) {
        this.school = school;
        this.medicine_name = medicine_name;
        this.usage = usage;
        this.unit = unit;
        this.quantity = quantity;
        this.capa = capa;
        this.capaXquantity = capaXquantity;
    }
}
