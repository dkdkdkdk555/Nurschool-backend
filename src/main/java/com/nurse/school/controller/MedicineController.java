package com.nurse.school.controller;

import com.nurse.school.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/manager/medicine")
public class MedicineController {

    /**
     *  약품 재고 조회 API
     *  - 전체 조회
     *  - 약품 이름으로 단건 조회
     */
    @GetMapping("")
    public ResponseEntity<Result> getMedicineList(){
        return null;
    }

    /**
     * 약품 재고 단건 추가 API
     *  - 수기등록
     */
    @PutMapping("")
    public ResponseEntity<Result> addOneMedicine(){
        return null;
    }

    /**
     * 약품 재고 엑셀업로드 API
     */
    @PostMapping("/addExcel")
    public ResponseEntity<Result> addMedicineByExcel(){
        return null;
    }

    /**
     * 약품 엑셀업로드 엑셀양식 다운로드 API
     */
    @GetMapping("/getExcelForm")
    public ResponseEntity<Resource> downloadExcelForm(){
        return null;
    }

    /**
     * 약품 재고 삭제 API
     *  - 단건 삭제
     *  - 복수건 삭제 (2&3) &으로 구분
     */
    @DeleteMapping("/{medicIds}")
    public ResponseEntity<Result> deleteMedicineInfo(@PathVariable("medicIds") String medicIds){
        return null;
    }

    /**
     * 약품 재고 수정 API
     *  - 단건 수정만
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Result> updateMedicineInfo(@PathVariable("id") Long id){
        return null;
    }

}
