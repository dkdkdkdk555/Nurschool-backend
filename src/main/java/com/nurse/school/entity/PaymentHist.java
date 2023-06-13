package com.nurse.school.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 결제 내역
 */
@Entity
@Table(name = "payment_history")
public class PaymentHist {

    @Id @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne // 다대일 단방향
    @JoinColumn(name = "user_id")
    private User user; // FK

    private String payment_amount; //결제금액
    private LocalDateTime payment_time; //결제일시


}
