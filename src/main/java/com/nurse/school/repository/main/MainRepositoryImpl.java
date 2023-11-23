package com.nurse.school.repository.main;

import com.nurse.school.dto.main.HealthDocumentAidDto;
import com.nurse.school.dto.main.HealthDocumentDto;
import com.nurse.school.dto.main.HealthDocumentSympDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.nurse.school.entity.QMain.main;
import static com.nurse.school.entity.QSymp.symp;
import static com.nurse.school.entity.QAid.aid1;

public class MainRepositoryImpl implements MainRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MainRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * 개인별 보건일지 목록 조회용 한줄씩만 나옴
     */
    @Override
    public Page<HealthDocumentDto> findListByid(Long personId, Pageable pageable) {
        JPQLQuery<HealthDocumentDto> query = queryFactory
                .select(
                        Projections.constructor(
                                HealthDocumentDto.class,
                                main.school.id,
                                main.person.id,
                                main.id,
                                main.memo,
                                main.visit_time,
                                Projections.list(
                                        Projections.constructor(
                                                HealthDocumentSympDto.class,
                                                symp.main.id,
                                                symp.id,
                                                symp.symptoms,
                                                symp.symptoms_detail,
                                                symp.body_part,
                                                Projections.list(
                                                        Projections.constructor(
                                                                HealthDocumentAidDto.class,
                                                                aid1.symp.id,
                                                                aid1.id,
                                                                aid1.aid,
                                                                aid1.aid_detail
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .from(main)
                .leftJoin(symp).on(main.id.eq(symp.main.id))
                .leftJoin(aid1).on(symp.id.eq(aid1.symp.id))
                .where(main.person.id.eq(personId))
                .groupBy(main.id) // 보건일지 별로 그룹바이 해줘서 보건일지 하나에 해당하는 상위 목록만 나옴
                .orderBy(main.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<HealthDocumentDto> resultList = query.fetch();

        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    @Override
    public List<Integer> findVisitNum(Long personId, LocalDate startDate, LocalDate endDate) {

        return queryFactory.select(main.visit_time.yearMonth())
                .from(main)
                .where(
                        main.person.id.eq(personId),
                        main.visit_time.between(
                                LocalDateTime.of(endDate, LocalTime.MAX),
                                LocalDateTime.of(startDate, LocalTime.MIN)
                        )
                )
                .fetch();

    }

}