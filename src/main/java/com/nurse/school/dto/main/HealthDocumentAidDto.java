package com.nurse.school.dto.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * [보건일지 처치] dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDocumentAidDto {

    private Long symptomsId;
    private Long aidsId;
    private String aid;
    private String aid_detail;

    public HealthDocumentAidDto(String aid, String aid_detail) {
        this.aid = aid;
        this.aid_detail = aid_detail;
    }
}
