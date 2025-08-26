package com.example.sky.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MenuSearchDto {
    @JsonProperty("merchantId")
    String merchantId;
    @JsonProperty("MenuName")
    String menuName;
    @JsonProperty("pageNum")
    int pageNum;
    @JsonProperty("pageSize")
    int pageSize;
}
