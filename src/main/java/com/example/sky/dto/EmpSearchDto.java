package com.example.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmpSearchDto {
    @JsonProperty("merchantId")
    String merchantId;
    @JsonProperty("empName")
    String empName;
    @JsonProperty("pageNum")
    int pageNum;
    @JsonProperty("pageSize")
    int pageSize;

}
