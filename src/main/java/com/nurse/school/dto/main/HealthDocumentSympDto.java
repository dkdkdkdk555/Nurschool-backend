package com.nurse.school.dto.main;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * [보건일지 증상] dto
 * - 보건일지 처치 dto 를 List 로 가지고 있음
 */
@Data
@NoArgsConstructor
public class HealthDocumentSympDto {

    private Long documentId;
    private Long symptomsId;

    private String symptoms;
    private String symptoms_detail;
    private String body_part;

    private List<HealthDocumentAidDto> aidList;
}
