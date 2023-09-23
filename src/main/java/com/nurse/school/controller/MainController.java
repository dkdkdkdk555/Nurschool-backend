package com.nurse.school.controller;

import com.nurse.school.response.Result;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/main")
public class MainController {

    /**
     * 보건일지 조회 API
     * - 학생기본정보
     * - 월별 방문 통계
     * - 보건일지 기록 list
     */
    @GetMapping("/healthdoc")
    public ResponseEntity<Page> getHealthDocInfo(){
        return null;
    }

    @PutMapping
    public ResponseEntity<Result> createHealthDoc(){
        return null;
    }
}
