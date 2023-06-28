package com.nurse.school.service;


import com.nurse.school.dto.JoinDto;
import com.nurse.school.entity.School;
import com.nurse.school.entity.User;
import com.nurse.school.exception.NoCreatDataException;
import com.nurse.school.repository.SchoolRepository;
import com.nurse.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    // 회원가입
    @Transactional(readOnly = false)
    public void join(JoinDto dto){
        User user = null;
        // 이미 등록된 학교정보가 있는지 검증
        School schoolInfo = schoolRepository.findByBizNum(dto.getSchool_biz_num());
        if(schoolInfo == null) {// 없으면
            // 신규 학교(워크스페이스)정보 생성
            School school = schoolRepository.save(makeSchool(dto));
            if(school == null) throw new NoCreatDataException("회원 가입에 실패하였습니다.");
            // 유저 엔티티 생성
            user = makeUser(dto, school);
            // 권한 생성 및 부여
            user.setRoles("ROLE_ADMIN");
        } else if(schoolInfo != null){// 있으면
            // 기존 학교 있을 경우, 유저 정보에 FK로 넣음
            user = makeUser(dto, schoolInfo);
            // 워크스페이스 이용 권한은 관리자 혹은 같은 학교 선생이 부여할때 넣어주고 지금은 USER 권한 넣어줌
            user.setRoles("ROLE_USER");
        }
        userRepository.save(user);
        if(user.getId() == null) throw new NoCreatDataException("회원 가입에 실패하였습니다.");
    }

    private User makeUser(JoinDto dto, School school) {
        return User.builder().name(dto.getName()).loginId(dto.getId())
                .password(dto.getPw()).sign_terms_yn(dto.getSign_terms_yn())
                .ad_terms_yn(dto.getAd_terms_yn()).payYn("N")
                .user_tel(dto.getTel_no()).school(school).build();
    }

    private School makeSchool(JoinDto dto) {
        return School.builder()
                .name(dto.getSchool_name())
                .bizNum(dto.getSchool_biz_num())
                .schoolTel(dto.getSchool_tel())
                .schoolAddr(dto.getSchool_addr())
                .build();
    }

}
