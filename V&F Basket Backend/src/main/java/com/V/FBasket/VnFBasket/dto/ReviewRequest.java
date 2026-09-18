package com.V.FBasket.VnFBasket.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ReviewRequest {
    private Long productId;
    @Min(1)
    @Max(5)
    private Long rating;
    private String comment;
}
