package com.nurse.school.service;

import com.nurse.school.dto.main.HealthDocumentDto;
import com.nurse.school.entity.Main;
import com.nurse.school.exception.NotFoundException;
import com.nurse.school.repository.main.MainRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    private final MainRepository mainRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Page<HealthDocumentDto> getDocumentList(Long personId, int page) throws NotFoundException{
        // 페이징 객체 초기화
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "document_id"));
        // 페이징 보건일지 리스트 가져오기
        Page<Main> pages = mainRepository.findListByid(personId, pageRequest);
        if(pages.getContent().isEmpty()){
            throw new NotFoundException("NOT FOUND DATA");
        }
        Page<HealthDocumentDto> toMap = pages.map(m -> modelMapper.map(m, HealthDocumentDto.class));
        return toMap;
    }


}
