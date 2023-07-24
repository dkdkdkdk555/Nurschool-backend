package com.nurse.school.repository;

import com.nurse.school.dto.PersonInsertDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;


import static com.nurse.school.entity.QPerson.person;

public class PersonRepositoryImpl implements PersonRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PersonRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override // 엑셀업로드시 이름+개인번호 일치하면 등록대신 수정
    public long updateWhenExcelUpload(PersonInsertDto dto) {
       return  queryFactory
                .update(person)
                .set(person.grade, dto.getGrade())
                .set(person.class_id, dto.getClass_id())
                .set(person.clss, dto.getClasses())
                .where(person.name.eq(dto.getName()).and(person.permanent_id.eq(dto.getPerman_id())))
                .execute();
    }

    @Override // 수기 수정
    public long updateDirect(PersonInsertDto dto, Long id) {
        return queryFactory
                .update(person)
                .set(person.grade, dto.getGrade())
                .set(person.class_id, dto.getClass_id())
                .set(person.clss, dto.getClasses())
                .set(person.name, dto.getName())
                .set(person.gender, dto.getGender())
                .set(person.patient_yn, dto.getPatient_yn())
                .set(person.permanent_id, dto.getPerman_id())
                .where(person.id.eq(id))
                .execute();
    }


}
