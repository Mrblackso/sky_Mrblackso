package com.example.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDto {

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Role")
    private String role;

    // getters and setters
}

