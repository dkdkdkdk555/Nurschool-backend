package com.nurse.school.service;

import com.nurse.school.dto.person.PersonDto;
import com.nurse.school.dto.person.PersonResponseDto;
import com.nurse.school.entity.Person;
import com.nurse.school.entity.School;
import com.nurse.school.exception.DoesntMatchExcelFormException;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.exception.NotFoundException;
import com.nurse.school.repository.PersonRepository;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final SchoolRepository schoolRepository;
    private final ExcelUtil excelUtil;

    @Transactional(readOnly = false)
    public PersonResponseDto insertPerson(PersonDto dto) throws NoCreationDataException{
        // 중복 등록 여부 판단
        Person dupl = personRepository.findPersonByPermanent_id(dto.getPerman_id());
        if(dupl != null){
            throw new NoCreationDataException("이미 등록된 학생/교직원이 있습니다!");
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
    public int insertPersonbyExcel(MultipartFile file, Long schoolId) throws DoesntMatchExcelFormException {
        // 파일이 존재하지 않는 경우
        if(file.isEmpty()){
            throw new DoesntMatchExcelFormException("파일이 존재하지 않습니다! 파일을 업로드해 주세요.");
        }

        // 확장자 유효성 검사 -> 엑셀파일만 가능
        String contentType = FileNameUtils.getExtension(file.getOriginalFilename());
        if(!contentType.equals("xlsx") && !contentType.equals("xls")){
            throw new DoesntMatchExcelFormException("잘못된 형식의 파일입니다! Excel 파일을 선택해 주세요.");
        }

        List<PersonDto> personList = new ArrayList<>();

        // 엑셀의 셀데이터를 가져와서 dto에 담기
        List<Map<String, Object>> listMap = excelUtil.getListData(file, 1, 6);

        if(listMap == null){
            throw new DoesntMatchExcelFormException("Excel 양식이 일치하지 않습니다. 올바른 양식의 Excel 파일을 업로드해 주세요.");
        }

        for (Map<String, Object> map : listMap) {
            PersonDto dto = new PersonDto();

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
        for (PersonDto dto : personList) {
            // 고유번호 + 이름 일치하는거 있을경우 업데이트 || 신규는 등록
            Person person = personRepository.findPersonByNameAndPermanent_id(dto.getName(), dto.getPerman_id());
            if(person != null){ // 학년, 반, 번호 등 만 수정
                personRepository.updateWhenExcelUpload(dto);
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
        return count; // 등록건수 알림

    }

    @Transactional
    public Page<PersonDto> getPeopleList(PersonDto dto) throws NotFoundException{
        PageRequest pageRequest = PageRequest.of(dto.getPage(), 5, Sort.by(Sort.Direction.ASC, "person_id"));
        Page<Person> pages = personRepository.findByPersonDto(dto, pageRequest);
        if(pages.getContent().isEmpty()){
            throw new NotFoundException("검색결과가 존재하지 않습니다.");
        }
        Page<PersonDto> toMap = pages.map(p -> new PersonDto(p.getSchool().getId(), p.getId(), p.getGrade(), p.getClss(), p.getClass_id(),
                p.getName(), p.getPermanent_id(), p.getGender(), p.getPersontype(), p.getPatient_yn()));
        return toMap;
    }

    @Transactional
    public Person updatePerson(Long personId, PersonDto dto) throws RuntimeException {
        long s = personRepository.updateDirect(dto, personId);
        if(s>0){
            Optional<Person> opt = personRepository.findById(personId);
            Person person = opt.get();
            return person;
        } else{
            throw new RuntimeException("학생교직원 정보 수정에 실패하였습니다.");
        }
    }

    @Transactional
    public String deletePerson(String personIds) throws NotFoundException {
        if(personIds.contains("&")){ // 복수건
            String[] personsArr = personIds.split("&");
            List<Long> list = new ArrayList<>();
            for (String s : personsArr) {
                list.add(Long.parseLong(s));
            }
            int n = personRepository.deletePersonsByIds(list);
            return n + "건 삭제";
        } else { // 단건
            Optional<Person> person = personRepository.findById(Long.parseLong(personIds));
            if(!person.isEmpty()){
                personRepository.delete(person.get());
                return "삭제완료";
            } else{
                throw new NotFoundException("삭제할 데이터가 존재하지 않습니다.");
            }
        }
    }

    private Person makePersonInfo(PersonDto dto, School school){
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
