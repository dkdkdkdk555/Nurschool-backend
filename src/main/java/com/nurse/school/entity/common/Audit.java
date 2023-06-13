package com.nurse.school.entity.common;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * 데이터 생성 및 수정 내역을 추적하는
 * 공통필드를 관리하는 내장타입(임베디드 타입)
 */
@Embeddable
public class Audit {

    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;

    public Audit() {
    }
}
