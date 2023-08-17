package com.nurse.school.service;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.entity.Medicine;
import com.nurse.school.exception.NoCreationDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineService {

    @Transactional
    public MedicineDto InsertNewMedicine(MedicineDto dto) throws NoCreationDataException {
        // 중복 등록 여부 판단 (동일 이름 약 x)

        return null;
    }
}
