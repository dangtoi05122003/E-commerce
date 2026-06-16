package com.E_commerce.Entity;

import com.E_commerce.Enum.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="payment")
@Getter
@Setter
public class PaymentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="order_id", nullable=false)
    private OrderEntity order;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String transactionId;
}
