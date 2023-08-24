package com.nurse.school.repository.person;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonRepositoryCustom {

    long updateWhenExcelUpload(PersonDto dto);

    long updateDirect(PersonDto dto, Long id);

    Page<Person> findByPersonDto(PersonDto dto, Pageable pageable);

    List<Person> findByPersonDto(PersonDto dto);
}
