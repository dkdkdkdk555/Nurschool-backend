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

    @Override
    public long update(PersonInsertDto dto) {
       return  queryFactory
                .update(person)
                .set(person.grade, dto.getGrade())
                .set(person.class_id, dto.getClass_id())
                .set(person.clss, dto.getClasses())
                .where(person.name.eq(dto.getName()).and(person.permanent_id.eq(dto.getPerman_id())))
                .execute();
    }
}
