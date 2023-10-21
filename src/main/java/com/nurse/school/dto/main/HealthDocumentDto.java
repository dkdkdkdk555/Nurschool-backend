package com.nurse.school.dto.main;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * [보건일지] dto
 * - 보건일지 증상 dto 를 List 로 가지고 있음
 */
@Data
@NoArgsConstructor
public class HealthDocumentDto {

    private Long schoolId;
    private Long personId;
    private Long documentid;

    private String memo;
    private LocalDateTime visit_time;

    private List<HealthDocumentSympDto> sympList;


}
