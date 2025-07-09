package com.example.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EmpDeleteDto {
    @JsonProperty("merchantId")
    String MerchantId;
    @JsonProperty("empIds")
    List<Integer> EmpIds;
}
