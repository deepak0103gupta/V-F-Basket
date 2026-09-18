package com.V.FBasket.VnFBasket.model;

import com.V.FBasket.VnFBasket.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private String paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String transactionId;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Orders order;


}
