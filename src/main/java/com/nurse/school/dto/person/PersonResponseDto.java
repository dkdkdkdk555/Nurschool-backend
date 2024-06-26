package com.nurse.school.dto.person;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [학생교직원 등록조회 페이지]
 * Person 정보 Response용 dto
 */
@Data
@NoArgsConstructor
public class PersonResponseDto {
    private String name;
    private Long id; // person_id

    @Builder
    public PersonResponseDto(String name, Long id) {
        this.name = name;
        this.id = id;
    }
}
