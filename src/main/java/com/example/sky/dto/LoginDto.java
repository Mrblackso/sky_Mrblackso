package com.example.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginDto {
    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Role")
    private String role;
}
