package com.nurse.school.repository.main;

import com.nurse.school.dto.main.HealthDocuListDto;
import com.nurse.school.dto.main.HealthDocumentDto;
import com.nurse.school.entity.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MainRepositoryCustom {

    Page<HealthDocumentDto> findListByid(Long personId, Pageable pageable);
}
