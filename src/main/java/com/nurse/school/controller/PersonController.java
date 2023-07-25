package com.nurse.school.controller;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.dto.person.PersonResponseDto;
import com.nurse.school.entity.Person;
import com.nurse.school.response.Result;
import com.nurse.school.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
public class PersonController {

    @Autowired PersonService personService;
    // 파일 다운로드를 위한 io 유틸
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

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
    public ResponseEntity<Result> upload(@RequestBody PersonDto dto){ // 응답표준화 o
        PersonResponseDto personResponseDto = personService.insertPerson(dto);
        return new ResponseEntity(new Result<PersonResponseDto>(personResponseDto), HttpStatus.CREATED);
    }

    /**
     * 학생, 교직원 목록 조회
     * -페이징
     */
    @PostMapping("/manager/students")
    public ResponseEntity<Page> peopleList(@RequestBody PersonDto dto){
        // 조회조건 학교-(이름, 반, 번호, 학생개인번호, 학생/교직원, 학년, 요양호자 등), 아무조건 없이 조회 경우 학교-데이터 생성 최근꺼 부터 DESC
        Page<PersonDto> result = personService.getPeopleList(dto);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 학생, 교직원 목록 조회
     * -엑셀다운로드
     */
    @PostMapping("/manager/students/download")
    public ResponseEntity<Resource> peopleListToExcel(@RequestBody PersonDto dto, HttpServletResponse res) {
        // 조회조건 학교-(이름, 반, 번호, 학생개인번호, 학생/교직원, 학년, 요양호자 등), 아무조건 없이 조회 경우 학교-데이터 생성 최근꺼 부터 DESC
        try {
            personService.getPeopleListForExport(dto, res);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } // ControllerAdvice에서 왜 처리 못해주나,, 알아봐야함
    }

    /**
     * 수정
     * -일단 단건 수정
     * 200
     */
    @PostMapping("/manager/student/{personId}")
    public ResponseEntity<Result> updatePersonInfo(@PathVariable("personId") Long personId,
                                                   @RequestBody PersonDto dto){

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
     * -단건&복수건 삭ㅈ[
     * http://localhost:8080/manager/student/2&3 - &으로 구분
     */
    @DeleteMapping("/manager/student/{personIds}")
    public ResponseEntity<Result> deletePersonInfo(@PathVariable("personIds") String personIds){ // 응답표준화 O
        String result = personService.deletePerson(personIds);
        return new ResponseEntity<>(new Result(result), HttpStatus.NO_CONTENT);
    }

    /**
     * 엑셀업로드 양식 파일 다운로드
     * @return ExcelUploadForm_Nurschool.xlsx
     */
    @GetMapping("/manager/getExcelForm")
    public ResponseEntity<Resource> downloadExcelForm(){
        try {
            // 파일 실제 경로 지정 및 로드
            Resource resource = resourceLoader.getResource("classpath:" + "file/ExcelUploadForm_Nurschool.xlsx");

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

}
