package com.V.FBasket.VnFBasket.dto;

import lombok.Data;

@Data
public class PlaceOrderRequestDTO {
    private Long addressId;

    public PlaceOrderRequestDTO(Long addressId) {
        this.addressId = addressId;
    }

}
