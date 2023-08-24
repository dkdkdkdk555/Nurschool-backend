package com.nurse.school.repository.medicine;

import com.nurse.school.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MedicineRepositoryCustom {

    Page<Medicine> findAllMedicine(String schoolId, PageRequest pageRequest);
}
