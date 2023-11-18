package com.nurse.school.service;

import com.nurse.school.dto.main.HealthDocumentAidDto;
import com.nurse.school.dto.main.HealthDocumentDto;
import com.nurse.school.dto.main.HealthDocumentSympDto;
import com.nurse.school.entity.*;
import com.nurse.school.exception.NotFoundException;
import com.nurse.school.repository.SchoolRepository;
import com.nurse.school.repository.main.AidRepository;
import com.nurse.school.repository.main.MainRepository;
import com.nurse.school.repository.main.SympRepository;
import com.nurse.school.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    private final PersonRepository personRepository;
    private final SchoolRepository schoolRepository;
    private final MainRepository mainRepository;
    private final SympRepository sympRepository;
    private final AidRepository aidRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Page<HealthDocumentDto> getDocumentList(Long personId, int page) throws NotFoundException{
        // 페이징 객체 초기화
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "document_id"));
        // 페이징 보건일지 리스트 가져오기
        Page<HealthDocumentDto> pages = mainRepository.findListByid(personId, pageRequest);
        if(pages.getContent().isEmpty()){
            throw new NotFoundException("NOT FOUND DATA");
        }
        return pages;
    }

    @Transactional
    public Long insertDocument(HealthDocumentDto dto){
        //TODO: 중복검사 : 보류, 이용 할 만한 칼럼이 없음..

        //TODO: 인원정보 가져오기
        Optional<School> school = schoolRepository.findById(dto.getSchoolId());
        Optional<Person> person = personRepository.findById(dto.getPersonId());

        //TODO: 보건일지 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Main main = mainRepository.save(Main.builder()
                                .person(person.get())
                                .school(school.get())
                                .visit_time(LocalDateTime.parse(dto.getVisit_time(), formatter)) // 방문시간과 작성시간이 다를 수 있으므로 프론트에서 입력받는다.
                                .memo(dto.getMemo()).build());
        //TODO: 증상 저장
        List<HealthDocumentSympDto> sympList = dto.getSympList();
        for (HealthDocumentSympDto sympDto : sympList) {

            Symp symp = sympRepository.save(Symp.builder()
                    .main(main)
                    .symptoms(sympDto.getSymptoms())
                    .body_part(sympDto.getBody_part())
                    .symptoms_detail(sympDto.getSymptoms_detail()).build());

            List<HealthDocumentAidDto> aidList = sympDto.getAidList();
            //TODO: 처치 저장
            for (HealthDocumentAidDto aidDto : aidList) {
                Aid aid = aidRepository.save(Aid.builder()
                        .symp(symp)
                        .aid(aidDto.getAid())
                        .aid_detail(aidDto.getAid_detail()).build());
            }
        }
        return main.getId();
    }

    @Transactional
    public Map<Integer, Integer> getStatistics(Long personId){
        // 일단은 personId 로만 찾자..

        //TODO: 셀렉트 시간 계산
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusMonths(6);

        List<Integer> list = mainRepository.findVisitNum(personId, startDate, endDate);

        Map<Integer, Integer> resultMap = new HashMap<>();

        int cm = 0, count = 0;
        for (int i = 0; i < list.size(); i++) {
            if(i>0){
                if(cm==list.get(i)) {
                    count++;
                    if(i==(list.size()-1)){
                        // 마지막 인덱스는 map저장
                        resultMap.put(cm, count);
                    }
                } else {
                    resultMap.put(cm, count);
                    count = 1;
                    cm = list.get(i);
                }
            } else {
                cm = list.get(i);
                count++; // 맨 처음꺼 숫자 카운트
            }

        }
        return resultMap;
    }
}
