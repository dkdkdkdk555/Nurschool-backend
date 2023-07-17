package com.nurse.school.dto;


import com.nurse.school.entity.School;
import com.nurse.school.entity.common.Persontype;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [학생교직원 등록조회 페이지]
 * 인원정보 테이블 PERSON_INFO
 * INSERT 용 dto
 */
@Data
@NoArgsConstructor
public class PersonInsertDto {
    // 학교
    @NotNull
    private Long schoolId; // 사용자가 쥐고잇음
    // 학년
    @NotNull
    private String grade;
    // 반
    private String classes;
    // 번호
    private int class_id;
    // 이름
    @NotNull
    private String name;
    // 학생개인번호
    @NotNull
    private String perman_id;
    // 성별
    @NotNull
    private String gender;
    // 학생/교직원 구분
    private Persontype persontype; // STAFF or STUDENT 널일 경우 학생으로
    // 요양호자 여부
    private String patient_yn; // 없으면 N

}
