package com.nurse.school.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 *  메인페이지 보건일지 목록 리턴용
 */
public class HealthDocuListDto {

    private long documentId;
    private String memo;
    private LocalDateTime visit_time;
    private String symptoms;
    private String aid;
    private String createdBy;

}
