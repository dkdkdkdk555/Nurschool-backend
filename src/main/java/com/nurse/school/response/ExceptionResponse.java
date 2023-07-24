package com.nurse.school.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private Date timestamp; // 에러 발생 시간
    private String message; // 에러 메세지
    private String details; // 에러 메세지 상세
}
