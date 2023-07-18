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

    /**
     * 엑셀업로드 등록
     */
    @PostMapping("/manager/addExcel")
    public String excelUpload(HttpServletRequest request, HttpServletResponse response,
                              MultipartFile file, Long schoolId){ // 엑셀업로드 등록
        return personService.insertPersonbyExcel(file, schoolId);
    }

    /**
     * 수기 등록
     */
    @PutMapping("/manager/student")
    public @ResponseBody PersonResponseDto upload(@RequestBody PersonInsertDto dto){ // 수기 등록

        PersonResponseDto personResponseDto = personService.insertPerson(dto);

        return personResponseDto;
    }

    @GetMapping("/manager/student")
    public String peopleList(){ // 학생,교직원 목록 조회 - 페이지 조회
        return "";
    }

    /**
     * 수정
     */
    @PostMapping("/manager/student/{personId}")
    public String updatePersonInfo(@PathVariable("personId") Long personId,
                                   @RequestBody PersonInsertDto dto){ // 학생교직언 정보 수정
        return personService.updatePerson(personId, dto);
    }

    /**
     * 삭제
     */
    @DeleteMapping("/manager/student/{personIds}")
    public String deletePersonInfo(@PathVariable("personIds") String personIds){ // 인원목록에서 삭제 - 단건 삭제
        return personService.deletePerson(personIds);
    }
}
