package com.nurse.school.repository.medicine;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface MedicineRepositoryCustom {

    Page<Medicine> findByMedicineDto(MedicineDto dto, Pageable pageable);

    long updateDirect(MedicineDto dto, Long id);

}
