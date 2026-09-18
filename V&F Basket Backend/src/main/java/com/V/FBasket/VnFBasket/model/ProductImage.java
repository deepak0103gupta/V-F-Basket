package com.V.FBasket.VnFBasket.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductImage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long imageId;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "productId")
    private Products product;

}
