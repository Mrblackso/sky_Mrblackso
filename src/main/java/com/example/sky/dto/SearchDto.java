package com.example.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

@Data
public class SearchDto {
    @JsonProperty("merchantId")
    String merchantId;
    @JsonProperty("empName")
    String empName;
    @JsonProperty("pageNum")
    int pageNum;
    @JsonProperty("pageSize")
    int pageSize;
}
