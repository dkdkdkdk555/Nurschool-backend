package com.nurse.school.repository.medicine;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.entity.Medicine;
import com.nurse.school.entity.Person;
import com.nurse.school.entity.QMedicine;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.nurse.school.entity.QMedicine.medicine;
import static com.nurse.school.entity.QPerson.person;

public class MedicineRepositoryImpl implements MedicineRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public MedicineRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Medicine> findByMedicineDto(MedicineDto dto, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if(dto.getSchoolId() != null){ // 학교
            builder.and(medicine.school.id.eq(dto.getSchoolId()));
        }
        if(dto.getMedicine_name() != null){ // 약품 이름
            builder.and(medicine.medicine_name.eq(dto.getMedicine_name()));
        }


        QueryResults<Medicine> queryResults = queryFactory
                .selectFrom(medicine)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public long updateDirect(MedicineDto dto, Long id) {

        JPAUpdateClause clause = queryFactory.update(medicine)
                .set(medicine.quantity, dto.getQuantity())
                .set(medicine.capa, dto.getCapa())
                .set(medicine.capaXquantity, dto.getCapa() * dto.getQuantity())
                .where(medicine.id.eq(id).and(medicine.medicine_name.eq(dto.getMedicine_name())));
        if(dto.getMedicine_name() != null){
            clause.set(medicine.medicine_name, dto.getMedicine_name());
        }
        if(dto.getUsage() != null){
            clause.set(medicine.usage, dto.getUsage());
        }
        if(dto.getUnit() != null) {
            clause.set(medicine.unit, dto.getUnit());
        }
        return clause.execute();
    }


}
