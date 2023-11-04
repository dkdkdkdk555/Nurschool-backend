package com.nurse.school.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * [보건일지 증상] dto
 * - 보건일지 처치 dto 를 List 로 가지고 있음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDocumentSympDto {

    private Long documentId;
    private Long id;

    private String symptoms;
    private String symptoms_detail;
    private String body_part;

    private List<HealthDocumentAidDto> aidList;

    public HealthDocumentSympDto(String symptoms, String body_part, List<HealthDocumentAidDto> aidList) {
        this.symptoms = symptoms;
        this.body_part = body_part;
        this.aidList = aidList;
    }
}
