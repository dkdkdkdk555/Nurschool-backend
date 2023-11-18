package com.nurse.school.repository.main;

import com.nurse.school.dto.main.HealthDocumentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MainRepositoryCustom {

    Page<HealthDocumentDto> findListByid(Long personId, Pageable pageable);

    List<Integer> findVisitNum(Long personId, LocalDate startDate, LocalDate endDate);
}
