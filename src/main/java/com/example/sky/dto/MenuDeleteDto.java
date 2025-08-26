package com.example.sky.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MenuDeleteDto {
    @JsonProperty("merchantId")
    String MerchantId;
    @JsonProperty("menuIds")
    List<Integer> MenuIds;
}
