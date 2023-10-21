package com.nurse.school.dto.main;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [보건일지 처치] dto
 */
@Data
@NoArgsConstructor
public class HealthDocumentAidDto {

    private Long symptomsId;
    private Long aidsId;

    private String aid;
    private String aid_detail;
}
