package com.nurse.school.repository;

import com.nurse.school.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select p from Person p where p.permanent_id = :permanent_id")
    Person findPersonByPermanent_id(@Param("permanent_id") String permanent_id);
}
