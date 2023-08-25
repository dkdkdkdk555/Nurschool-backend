package com.nurse.school.service;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.entity.Medicine;
import com.nurse.school.entity.Person;
import com.nurse.school.entity.School;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.exception.NotFoundException;
import com.nurse.school.repository.medicine.MedicineRepository;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final SchoolRepository schoolRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public MedicineDto InsertNewMedicine(MedicineDto dto) throws NoCreationDataException {
        // 중복 등록 여부 판단 (동일 이름 약 x)
        Medicine dupl = medicineRepository.findMedicineByNameAndSchool(dto.getMedicine_name(), dto.getSchoolId());
        if(dupl != null){
            throw new NoCreationDataException("이미 등록된 재고가 있습니다. 기존 재고를 수정해주십시오.");
        }
        // 총 수량 계산
        int sum = dto.getCapa()*dto.getQuantity();
        dto.setCapaXquantity(sum);

        // 등록
        Optional<School> school = schoolRepository.findById(dto.getSchoolId());
        Medicine newMedicine = medicineRepository.save(makeMedicineStock(dto, school.get()));

        // 반환
        MedicineDto medicineDto = modelMapper.map(newMedicine, MedicineDto.class);

        return medicineDto;
    }

    @Transactional
    public Page<MedicineDto> getMedicineList(MedicineDto dto){
        PageRequest pageRequest = PageRequest.of(dto.getPage(), 10, Sort.by(Sort.Direction.DESC, "stock_id"));
        Page<Medicine> pages = medicineRepository.findByMedicineDto(dto, pageRequest);
        if(pages.getContent().isEmpty()){
            throw new NotFoundException("검색결과가 존재하지 않습니다.");
        }
        Page<MedicineDto> toMap = pages.map(m -> modelMapper.map(m, MedicineDto.class));
        return toMap;
    }

    private Medicine makeMedicineStock(MedicineDto dto, School school){
        return Medicine.builder()
                .school(school)
                .medicine_name(dto.getMedicine_name())
                .usage(dto.getUsage())
                .unit(dto.getUnit())
                .quantity(dto.getQuantity())
                .capaXquantity(dto.getCapaXquantity())
                .capa(dto.getCapa())
                .build();
    }
}
