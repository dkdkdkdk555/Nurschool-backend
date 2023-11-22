package com.nurse.school.dto.main;

import com.nurse.school.entity.Main;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * [보건일지] dto
 * - 보건일지 증상 dto 를 List 로 가지고 있음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDocumentDto {

    private Long schoolId;
    private Long personId;
    private Long id;

    private String memo;
    private String visit_time;

    private List<HealthDocumentSympDto> sympList;


    public HealthDocumentDto(Main main) {
        this.schoolId = main.getSchool().getId();
        this.personId = main.getPerson().getId();
        this.id = main.getId();
        this.memo = main.getMemo();
        this.visit_time = main.getVisit_time().toString();
//        this.sympList = getSympFromMain(main);
    }

    public HealthDocumentDto(Long id, String memo, String visit_time, List<HealthDocumentSympDto> sympList) {
        this.id = id;
        this.memo = memo;
        this.visit_time = visit_time;
        this.sympList = sympList;
    }
}
