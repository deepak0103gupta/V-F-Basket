package com.V.FBasket.VnFBasket.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;

    private String email;

    private String role;

    private Long userId;

    @JsonCreator
    public LoginResponse(@JsonProperty("token") String token,
                         @JsonProperty("email") String email,
                         @JsonProperty("role") String role,
                         @JsonProperty("userId") Long userId) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.userId = userId;
    }
}
