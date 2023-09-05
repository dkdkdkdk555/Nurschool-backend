package com.nurse.school.controller;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.dto.person.PersonResponseDto;
import com.nurse.school.entity.Person;
import com.nurse.school.response.Result;
import com.nurse.school.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping( "/manager/medicine")
public class MedicineController {

    @Autowired private MedicineService medicineService;
    // 파일 다운로드를 위한 io 유틸
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     *  약품 재고 조회 API
     *  - 전체 조회
     *  - 약품 이름으로 단건 조회
     */
    @PostMapping("/getMedicineList")
    public ResponseEntity<Page> getMedicineList(@RequestBody MedicineDto dto){
        Page<MedicineDto> result = medicineService.getMedicineList(dto);
        return ResponseEntity.ok().body(result);
    }

    /**
     * 약품 재고 단건 추가 API
     *  - 수기등록
     */
    @PutMapping
    public ResponseEntity<Result> addOneMedicine(@RequestBody MedicineDto dto){
        MedicineDto newMedicineDto = medicineService.InsertNewMedicine(dto);
        return new ResponseEntity(new Result<MedicineDto>(newMedicineDto), HttpStatus.CREATED);
    }

    /**
     * 약품 재고 엑셀업로드 API
     */
    @PostMapping("/addExcel")
    public ResponseEntity<Result> addMedicineByExcel(HttpServletRequest request, HttpServletResponse response,
                                                     MultipartFile file, Long schoolId){
        int count = medicineService.insertMedicineByExcel(file, schoolId);
        return new ResponseEntity<>(new Result(count), HttpStatus.CREATED);
    }

    /**
     * 약품 엑셀업로드 엑셀양식 다운로드 API
     */
    @GetMapping("/getExcelForm")
    public ResponseEntity<Resource> downloadExcelForm(){
        try {
            // 파일 실제 경로 지정 및 로드
            Resource resource = resourceLoader.getResource("classpath:" + "file/MedicineUploadForm_Nurschool.xls");

            // 파일이 존재하는지 확인
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 파일을 반환하기 위핸 ResponseEntity 생성
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            // 파일 로드에 실패한 경우
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * 약품 재고 삭제 API
     *  - 단건 삭제
     *  - 복수건 삭제 (2&3) &으로 구분
     */
    @DeleteMapping("/{stock_id}")
    public ResponseEntity<Result> deleteMedicineInfo(@PathVariable("stock_id") String stock_id){
        String result = medicineService.deleteMedicine(stock_id);
        return new ResponseEntity<>(new Result(result), HttpStatus.NO_CONTENT);
    }

    /**
     * 약품 재고 수정 API
     *  - 단건 수정만
     */
    @PostMapping("/{id}")
    public ResponseEntity<Result> updateMedicineInfo(@PathVariable("id") Long id,
                                                     @RequestBody MedicineDto dto){
        HttpHeaders headers = new HttpHeaders();
        MedicineDto medicine =  medicineService.updateMedicine(id, dto);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/manager/main/{id}")
                .buildAndExpand(medicine.getId())
                .toUri();
        headers.setLocation(location);


        return ResponseEntity.ok().headers(headers).body(new Result(medicine));
    }

}
