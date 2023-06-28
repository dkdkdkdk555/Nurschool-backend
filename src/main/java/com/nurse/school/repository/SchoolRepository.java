package com.nurse.school.repository;

import com.nurse.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

    School findByBizNum(String biznum);

    School save(School school);
}

