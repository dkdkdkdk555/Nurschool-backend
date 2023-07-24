package com.nurse.school.repository;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepositoryCustom {

    long updateWhenExcelUpload(PersonDto dto);

    long updateDirect(PersonDto dto, Long id);

    Page<Person> findByPersonDto(PersonDto dto, Pageable pageable);
}
