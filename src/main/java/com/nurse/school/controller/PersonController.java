package com.nurse.school.controller;

import com.nurse.school.dto.PersonInsertDto;
import com.nurse.school.dto.PersonResponseDto;
import com.nurse.school.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PersonController {

    @Autowired PersonService personService;

    @PostMapping("/manager/addExcel")
    public String excelUpload(HttpServletRequest request, HttpServletResponse response,
                              MultipartFile file, Long schoolId){ // 엑셀업로드 등록
        return personService.insertPersonbyExcel(file, schoolId);
    }

    @PutMapping("/manager/student") // 1
    public @ResponseBody PersonResponseDto upload(@RequestBody PersonInsertDto dto){ // 수기 등록

        PersonResponseDto personResponseDto = personService.insertPerson(dto);

        return personResponseDto;
    }

    @GetMapping("/manager/student") // 2
    public String peopleList(){ // 학생,교직원 목록 조회 - 페이지 조회
        return "";
    }

    @PostMapping("/manager/student/{personId}") // 3
    public String updatePersonInfo(){ // 학생교직언 정보 수정
        return "";
    }

    @DeleteMapping("/manager/student/{personId}") // 4
    public String deletePersonInfo(){ // 인원목록에서 삭제 - 단건 삭제
        return "";
    }

}
