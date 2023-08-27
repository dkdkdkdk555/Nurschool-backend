package com.nurse.school.service;

import com.nurse.school.dto.medicine.MedicineDto;
import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.dto.person.PersonResponseDto;
import com.nurse.school.entity.Medicine;
import com.nurse.school.entity.Person;
import com.nurse.school.entity.School;
import com.nurse.school.exception.DoesntMatchExcelFormException;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.exception.NotFoundException;
import com.nurse.school.repository.medicine.MedicineRepository;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final SchoolRepository schoolRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private final ExcelUtil excelUtil;

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

    @Transactional
    public int insertMedicineByExcel(MultipartFile file, Long schoolId) throws DoesntMatchExcelFormException, NumberFormatException {
        // 파일이 존재하지 않는 경우
        if(file.isEmpty()){
            throw new DoesntMatchExcelFormException("파일이 존재하지 않습니다! 파일을 업로드해 주세요.");
        }

        // 확장자 유효성 검사 -> 엑셀파일만 가능
        String contentType = FileNameUtils.getExtension(file.getOriginalFilename());
        if(!contentType.equals("xlsx") && !contentType.equals("xls")){
            throw new DoesntMatchExcelFormException("잘못된 형식의 파일입니다! Excel 파일을 선택해 주세요.");
        }

        List<MedicineDto> medicineList = new ArrayList<>();

        // 엑셀의 셀데이터를 가져와서 dto에 담기
        List<Map<String, Object>> listMap = excelUtil.getListData(file, 2, 6, "medicine", "HSSF");

        if(listMap == null){
            throw new DoesntMatchExcelFormException("Excel 양식이 일치하지 않습니다. 올바른 양식의 Excel 파일을 업로드해 주세요.");
        }

        for (Map<String, Object> map : listMap) {
            MedicineDto dto = new MedicineDto();

            int capa = Integer.parseInt(map.get("2").toString());
            int quan = Integer.parseInt(map.get("3").toString());

            // 각 셀의 데이터를 dto에 set
            dto.setSchoolId(schoolId);
            dto.setMedicine_name(map.get("1").toString());
//            dto.setUnit(map.get("2").toString()); 단위
            dto.setCapa(capa); // 규격
            dto.setQuantity(quan); // 수량
            dto.setCapaXquantity(capa * quan); // 총수량
            // TODO : 약품 API 호출해서 용도 받아내기

            medicineList.add(dto);
        }

        int count = 0;
        for (MedicineDto dto : medicineList) {

            // 중복재고는 insert 막음
            Medicine medi = medicineRepository.findMedicineByNameAndSchool(dto.getMedicine_name(), dto.getSchoolId());
            if(medi == null){
                // 등록
                Optional<School> school = schoolRepository.findById(dto.getSchoolId());
                medicineRepository.save(makeMedicineStock(dto, school.get()));

                count++;
            }

        }

        return count; // 등록건수 알림
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
