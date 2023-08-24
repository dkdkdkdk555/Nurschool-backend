package com.nurse.school.service;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.entity.Medicine;
import com.nurse.school.entity.School;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.repository.medicine.MedicineRepository;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final SchoolRepository schoolRepository;

    @Transactional
    public MedicineDto InsertNewMedicine(MedicineDto dto) throws NoCreationDataException {
        // 중복 등록 여부 판단 (동일 이름 약 x)
        Medicine dupl = medicineRepository.findMedicineByNameAndSchool(dto.getMedicine_name(), dto.getSchoolId());
        if(dupl != null){
            throw new NoCreationDataException("이미 등록된 재고가 있습니다. 기존 재고를 수정해주십시오.");
        }

        // 등록
        Optional<School> school = schoolRepository.findById(dto.getSchoolId());
        Medicine newMedicine = medicineRepository.save(makeMedicineStock(dto, school.get()));

        // 반환
        MedicineDto medicineDto = new MedicineDto(newMedicine);

        return medicineDto;
    }

    @Transactional
    public Page<Medicine> getMedicineList(int pageNum){
//        medicineRepository.findAll()
        return null;
    }

    private Medicine makeMedicineStock(MedicineDto dto, School school){
        return Medicine.builder()
                .school(school)
                .name(dto.getMedicine_name())
                .usage(dto.getUsage())
                .unit(dto.getUnit())
                .quantity(dto.getQuantity())
                .capa(dto.getCapa())
                .build();
    }
}
