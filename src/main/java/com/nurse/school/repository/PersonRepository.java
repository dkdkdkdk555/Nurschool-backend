package com.nurse.school.repository;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryCustom{

    @Query("select p from Person p where p.permanent_id = :permanent_id")
    Person findPersonByPermanent_id(@Param("permanent_id") String permanent_id);

    @Query("select p from Person p where p.name = :name and p.permanent_id = :permanent_id")
    Person findPersonByNameAndPermanent_id(@Param("name") String name, @Param("permanent_id") String permanent_id);

    @Modifying
    @Query("delete from Person p where p.id in :personIds")
    int deletePersonsByIds(@Param("personIds")List<Long> personIds);
}
