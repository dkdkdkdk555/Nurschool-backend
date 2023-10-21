package com.nurse.school.repository.main;
import com.nurse.school.entity.Main;
import com.nurse.school.entity.Person;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.nurse.school.entity.QMain.main;
import static com.nurse.school.entity.QSymp.symp;
import static com.nurse.school.entity.QAid.aid1;
public class MainRepositoryCustomImpl implements MainRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MainRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<Main> findListByid(Long personId, Pageable pageable) {

        // TODO: 증상 리스트 , 처치 리스트도 매핑해줄것

        QueryResults<Main> queryResults = queryFactory
                .selectFrom(main)
                .where(main.user.id.eq(personId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());

    }
}
