package com.nurse.school.service;

import com.nurse.school.dto.PersonInsertDto;
import com.nurse.school.dto.PersonResponseDto;
import com.nurse.school.entity.Person;
import com.nurse.school.entity.School;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.repository.PersonRepository;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final SchoolRepository schoolRepository;
    private final ExcelUtil excelUtil;

    @Transactional(readOnly = false)
    public PersonResponseDto insertPerson(PersonInsertDto dto){
        // 중복 등록 여부 판단
        Person dupl = personRepository.findPersonByPermanent_id(dto.getPerman_id());
        if(dupl != null){
            new NoCreationDataException("이미 등록된 학생/교직원이 있습니다!");
            return null;
        }

        // 등록
        Optional<School> school = schoolRepository.findById(dto.getSchoolId());
        Person newPerson = personRepository.save(makePersonInfo(dto, school.get()));

        // 반환
        PersonResponseDto responseDto = PersonResponseDto.builder()
                .name(newPerson.getName()).id(newPerson.getId()).build();

        return responseDto;
    }

    @Transactional
    public String insertPersonbyExcel(MultipartFile file, Long schoolId){
        // 파일이 존재하지 않는 경우
        if(file.isEmpty()){
            return "Excel 파일을 선택해주세요.1";
        }

        // 확장자 유효성 검사 -> 엑셀파일만 가능
        String contentType = FileNameUtils.getExtension(file.getOriginalFilename());
        if(!contentType.equals("xlsx") && !contentType.equals("xls")){
            return "Excel 파일을 선택해주세요.2";
        }

        List<PersonInsertDto> personList = new ArrayList<>();

        // 엑셀의 셀데이터를 가져와서 dto에 담기
        List<Map<String, Object>> listMap = excelUtil.getListData(file, 1, 6);

        if(listMap == null){
            return "엑셀 양식이 일치하지 않습니다.";
        }

        for (Map<String, Object> map : listMap) {
            PersonInsertDto dto = new PersonInsertDto();

            // 각 셀의 데이터를 dto에 set
            dto.setGrade(map.get("0").toString());
            dto.setClasses(map.get("1").toString());
            dto.setClass_id(Integer.parseInt(map.get("2").toString()));
            dto.setName(map.get("3").toString());
            dto.setPerman_id(map.get("4").toString());
            dto.setGender(map.get("5").toString());

            personList.add(dto);
        }

        Optional<School> school = schoolRepository.findById(schoolId);

        int count = 0;
        for (PersonInsertDto dto : personList) {
            // 고유번호 + 이름 일치하는거 있을경우 업데이트 || 신규는 등록
            Person person = personRepository.findPersonByNameAndPermanent_id(dto.getName(), dto.getPerman_id());
            if(person != null){ // 학년, 반, 번호 등 만 수정
                personRepository.update(dto);
                count++;
            } else {
                person = Person.builder().grade(dto.getGrade())
                        .clss(dto.getClasses())
                        .class_id(dto.getClass_id())
                        .name(dto.getName())
                        .permanent_id(dto.getPerman_id())
                        .gender(dto.getGender())
                        .school(school.get()).build();

                Person p = personRepository.save(person);
                if(p != null)  count++;
            }
        }


        return count + "건 등록완료!!!"; // 등록건수 알림
    }

    private Person makePersonInfo(PersonInsertDto dto, School school){
        return Person.builder()
                .school(school)
                .grade(dto.getGrade())
                .name(dto.getName())
                .permanent_id(dto.getPerman_id())
                .gender(dto.getGender())
                .clss(dto.getClasses())
                .class_id(dto.getClass_id())
                .persontype(dto.getPersontype())
                .patient_yn(dto.getPatient_yn() != null ? dto.getPatient_yn() : "N" )
                .build();

    }
}
