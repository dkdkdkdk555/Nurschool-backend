package com.nurse.school.service;

import com.nurse.school.entity.School;
import com.nurse.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    // 학교정보 생성
    @Transactional(readOnly = false)
    public Long join(School school){
        schoolRepository.save(school);
        return school.getId();
    }

    public Optional<School> findOne(Long id){
       return schoolRepository.findById(id);
    }
}
