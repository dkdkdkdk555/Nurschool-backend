package com.nurse.school.repository.person;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.entity.Person;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;

import java.util.List;
import static com.nurse.school.entity.QPerson.person;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PersonRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override // 엑셀업로드시 이름+개인번호 일치하면 등록대신 수정
    public long updateWhenExcelUpload(PersonDto dto) {
       return  queryFactory
                .update(person)
                .set(person.grade, dto.getGrade())
                .set(person.class_id, dto.getClass_id())
                .set(person.clss, dto.getClasses())
                .where(person.name.eq(dto.getName()).and(person.permanent_id.eq(dto.getPerman_id())))
                .execute();
    }

    @Override // 수기 수정
    public long updateDirect(PersonDto dto, Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        long updatedCount = queryFactory
                .update(person)
                .set(person.grade, dto.getGrade())
                .set(person.class_id, dto.getClass_id())
                .set(person.clss, dto.getClasses())
                .set(person.name, dto.getName())
                .set(person.gender, dto.getGender())
                .set(person.patient, dto.getPatient())
                .set(person.permanent_id, dto.getPerman_id())
                .where(person.id.eq(id))
                .execute();

        if (updatedCount > 0) {
            // 업데이트가 성공한 경우에만 Auditing 정보를 업데이트
            queryFactory
                    .update(person)
                    .set(person.updatedBy, authentication.getName())
                    .where(person.id.eq(id))
                    .execute();
        }

        return updatedCount;
    }

    @Override
    public Page<Person> findByPersonDto(PersonDto dto, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if(dto.getSchoolId() != null){ // 학교
            builder.and(person.school.id.eq(dto.getSchoolId()));
        }
        if(dto.getName() != null){ // 이름
            builder.and(person.name.eq(dto.getName()));
        }
        if(dto.getGrade() != null){ // 학년
            builder.and(person.grade.eq(dto.getGrade()));
        }
        if(dto.getClasses() != null){ // 반
            builder.and(person.clss.eq(dto.getClasses()));
        }
        if(dto.getClass_id() != 0){ // 번호
            builder.and(person.class_id.eq(dto.getClass_id()));
        }
        if(dto.getPerman_id() != null){ // 학생개인번호
            builder.and(person.permanent_id.eq(dto.getPerman_id()));
        }
        if(dto.getGender() != null){ // 성별
            builder.and(person.gender.eq(dto.getGender()));
        }
        if(dto.getPersontype() != null){ // 학생 및 교직원 여부
            builder.and(person.persontype.eq(dto.getPersontype()));
        }
        if(dto.getPatient() != null){ // 요양호자 여부
            builder.and(person.patient.eq(dto.getPatient()));
        }

        QueryResults<Person> queryResults = queryFactory
                .selectFrom(person)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());

    }

    @Override
    public List<Person> findByPersonDto(PersonDto dto) {
        BooleanBuilder builder = new BooleanBuilder();

        if(dto.getSchoolId() != null){ // 학교
            builder.and(person.school.id.eq(dto.getSchoolId()));
        }
        if(dto.getName() != null){ // 이름
            builder.and(person.name.eq(dto.getName()));
        }
        if(dto.getGrade() != null){ // 학년
            builder.and(person.grade.eq(dto.getGrade()));
        }
        if(dto.getClasses() != null){ // 반
            builder.and(person.clss.eq(dto.getClasses()));
        }
        if(dto.getClass_id() != 0){ // 번호
            builder.and(person.class_id.eq(dto.getClass_id()));
        }
        if(dto.getPerman_id() != null){ // 학생개인번호
            builder.and(person.permanent_id.eq(dto.getPerman_id()));
        }
        if(dto.getGender() != null){ // 성별
            builder.and(person.gender.eq(dto.getGender()));
        }
        if(dto.getPersontype() != null){ // 학생 및 교직원 여부
            builder.and(person.persontype.eq(dto.getPersontype()));
        }
        if(dto.getPatient() != null){ // 요양호자 여부
            builder.and(person.patient.eq(dto.getPatient()));
        }

        return queryFactory
                .selectFrom(person)
                .where(builder)
                .fetch();
    }

}
