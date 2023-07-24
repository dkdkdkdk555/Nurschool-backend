package com.nurse.school.controller;

import com.nurse.school.dto.PersonInsertDto;
import com.nurse.school.dto.PersonResponseDto;
import com.nurse.school.entity.Person;
import com.nurse.school.response.Result;
import com.nurse.school.service.PersonService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
public class PersonController {

    @Autowired PersonService personService;

    /**
     * 엑셀업로드 등록
     */
    @PostMapping("/manager/addExcel")
    public ResponseEntity<Result> excelUpload(HttpServletRequest request, HttpServletResponse response,
                              MultipartFile file, Long schoolId){ // 엑셀업로드 등록, 응답표준화 o
        int count = personService.insertPersonbyExcel(file, schoolId);
        return new ResponseEntity<>(new Result(count), HttpStatus.CREATED);
    }

    /**
     * 수기 등록
     */
    @PostMapping("/manager/student")
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Result> upload(@RequestBody PersonInsertDto dto){ // 응답표준화 o
        PersonResponseDto personResponseDto = personService.insertPerson(dto);
        return new ResponseEntity(new Result<PersonResponseDto>(personResponseDto), HttpStatus.CREATED);
    }

    /**
     * 학생, 교직원 목록 조회
     * -페이징
     */
    @GetMapping("/manager/student")
    public String peopleList(){ // 학생,교직원 목록 조회 - 페이지 조회
        return "";
    }

    /**
     * 수정
     * -일단 단건 수정
     * 200
     */
    @PostMapping("/manager/student/{personId}")
    public ResponseEntity<Result> updatePersonInfo(@PathVariable("personId") Long personId,
                                                   @RequestBody PersonInsertDto dto){

        HttpHeaders headers = new HttpHeaders();
        Person person =  personService.updatePerson(personId, dto);
        PersonResponseDto prd = new PersonResponseDto(person.getName(), person.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/manager/main/{id}")
                .buildAndExpand(person.getId())
                .toUri();
        headers.setLocation(location);


        return ResponseEntity.ok().headers(headers).body(new Result(prd));
    }

    /**
     * 삭제
     * -단건&복수건 수정
     * http://localhost:8080/manager/student/2&3 - &으로 구분
     */
    @DeleteMapping("/manager/student/{personIds}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Result> deletePersonInfo(@PathVariable("personIds") String personIds){ // 응답표준화 O
        String result = personService.deletePerson(personIds);
        return new ResponseEntity<>(new Result(result), HttpStatus.NO_CONTENT);
    }
}
