package com.nurse.school.repository.medicine;

import com.nurse.school.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class MedicineRepositoryImpl implements MedicineRepositoryCustom{

    @Override
    public Page<Medicine> findAllMedicine(String schoolId, PageRequest pageRequest) {



        return null;
    }
}
