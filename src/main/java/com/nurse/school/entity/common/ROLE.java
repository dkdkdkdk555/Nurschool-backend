package com.nurse.school.entity.common;

public enum ROLE { // 권한 승인 여부 상태
    ROLE_USER // 일반 사용자 (가입은 했는데 워크스페이스 사용 못하는)
    , ROLE_ADMIN // 워크스페이스 이용자 (권한허용처리가 완료된 사용자)
    , ROLE_MANAGER // 개발자, 시스템 관리자들
}
