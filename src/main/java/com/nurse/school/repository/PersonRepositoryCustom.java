package com.nurse.school.repository;

import com.nurse.school.dto.PersonInsertDto;

import java.util.List;

public interface PersonRepositoryCustom {

    long updateWhenExcelUpload(PersonInsertDto dto);

    long updateDirect(PersonInsertDto dto, Long id);
}
