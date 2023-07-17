package com.nurse.school.service;

import com.nurse.school.dto.PersonInsertDto;
import com.nurse.school.dto.PersonResponseDto;
import com.nurse.school.entity.Person;
import com.nurse.school.entity.School;
import com.nurse.school.entity.common.Persontype;
import com.nurse.school.exception.NoCreationDataException;
import com.nurse.school.repository.PersonRepository;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final SchoolRepository schoolRepository;

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
