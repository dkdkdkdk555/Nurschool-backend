package com.nurse.school.dto.medicine;

import com.nurse.school.entity.Medicine;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [보건실 관리 > 약품 관리]
 * 약품재고 테이블
 * ISERT 용
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {

    @NotNull
    private Long schoolId;
    private Long id;
    @NotNull
    private String medicine_name;
    private String usage;
    private String unit;
    private int quantity; // 몇 개
    private int capa; // 몇 박스
//    private String sideEffect;
    private int capaXquantity; // 박스 x 박스수 = 총 내용물 갯수

    private int page;

    public MedicineDto(Long schoolId, String medicine_name, String usage, String unit, int quantity, int capa, int page) {
        this.schoolId = schoolId;
//        this.id = id;
        this.medicine_name = medicine_name;
        this.usage = usage;
        this.unit = unit;
        this.quantity = quantity;
        this.capa = capa;
//        this.sideEffect = sideEffect;
        this.page = page;
    }

    public MedicineDto(Medicine medicine){
        this.medicine_name = medicine.getMedicine_name();
        this.unit = medicine.getUnit();
        this.usage = medicine.getUsage();
        this.quantity = medicine.getQuantity();
        this.capa = medicine.getCapa();
        this.capaXquantity = medicine.getCapaXquantity();
    }
}
