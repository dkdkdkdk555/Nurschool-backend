package com.nurse.school.dto.person;


import com.nurse.school.entity.School;
import com.nurse.school.entity.common.Persontype;
import com.sun.istack.NotNull;
import lombok.*;

/**
 * [학생교직원 등록조회 페이지]
 * 인원정보 테이블 PERSON_INFO
 * INSERT 용 dto
 */
@Data
@NoArgsConstructor
public class PersonDto {
    // 학교
    @NotNull
    private Long schoolId; // 사용자가 쥐고잇음
    private Long personId;
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
    private String patient; // null이면 요양호자 아닌거
    // 메모
    private String person_memo;

    // 페이징관련
    private int page;

    public PersonDto(Long schoolId, Long personId, String grade, String classes, int class_id, String name, String perman_id, String gender, Persontype persontype, String patient, String person_memo) {
        this.schoolId = schoolId;
        this.personId = personId;
        this.grade = grade;
        this.classes = classes;
        this.class_id = class_id;
        this.name = name;
        this.perman_id = perman_id;
        this.gender = gender;
        this.persontype = persontype;
        this.patient = patient;
        this.person_memo = person_memo;
    }
}
