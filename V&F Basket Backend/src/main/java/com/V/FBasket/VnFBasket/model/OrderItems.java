package com.V.FBasket.VnFBasket.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Products product;

    private Integer quantity;
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order;
}
