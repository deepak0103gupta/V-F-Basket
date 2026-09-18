package com.V.FBasket.VnFBasket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int stockQuantity;
    @Column(nullable = false)
    private int productRating = 0;

    @ManyToOne
    @JoinColumn(name="categoryId")
    private Categories category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reviews> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore 
    private List<OrderItems> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private ProductImage productImage;
}
