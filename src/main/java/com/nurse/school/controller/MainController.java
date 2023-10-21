package com.nurse.school.controller;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.response.Result;
import com.nurse.school.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/main")
public class MainController {

    @Autowired PersonService personService;

    /**
     * 1.
     * [학생, 교직원 검색] API
     * 처음 검색할때 복수건일 수 있다.
     * 단건 -> [보건일지List] 조회 API로 리다이렉트
     * 복수건 -> List<Dto> 목록 리턴 -> 사용자 선택
     */
    @GetMapping
    public ResponseEntity<Page> getPeopleInfo(PersonDto dto){

        Page<PersonDto> result = personService.getPeopleList(dto);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 2.
     * [보건일지List] API
     * 학생검색api에서 결과가 단건일 경우 해당 API로 리다이렉트 시키고
     * 복수건인 경우 하나를 선택하여 Key값(person_id)을 전달하여 해당 API를 호출한다.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result> getHealthInfo(@PathVariable("id") Long id,
                                                @RequestParam("page") int page){

        return null;
    }

    /**
     * 보건일지 작성 API
     */
    @PutMapping
    public ResponseEntity<Result> createHealthDoc(){
        return null;
    }

    /**
     * 월별방문횟수 통계결과 조회 API
     */
    @PutMapping
    public ResponseEntity<Result> visitStatistics(){
        return null;
    }
}
