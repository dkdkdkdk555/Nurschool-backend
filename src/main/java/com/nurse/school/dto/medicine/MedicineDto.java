package com.nurse.school.dto.medicine;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [보건실 관리 > 약품 관리]
 * 약품재고 테이블
 * ISERT 용
 */

@Data
@NoArgsConstructor
public class MedicineDto {

    @NotNull
    private Long schoolId;
    @NotNull
    private String medicine_name;
    private String usage;
    private String unit;
    private int quantity; // 몇 개
    private int capa; // 몇 박스
    private String sideEffect;

    private int page;

    public MedicineDto(Long schoolId, String medicine_name, String usage, String unit, int quantity, int capa, String sideEffect, int page) {
        this.schoolId = schoolId;
        this.medicine_name = medicine_name;
        this.usage = usage;
        this.unit = unit;
        this.quantity = quantity;
        this.capa = capa;
        this.sideEffect = sideEffect;
        this.page = page;
    }
}
