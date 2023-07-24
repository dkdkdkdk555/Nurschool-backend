package com.nurse.school.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * API 기본 응답 클래스
 * @param <T>
 */
@Data
@AllArgsConstructor
public class Result<T> {

    private T data;
    private int count;
    private String massage;

    public Result(T data) {
        this.data = data;
    }

    public Result(int count) {
        this.count = count;
    }

    public Result(String massage) {
        this.massage = massage;
    }
}
