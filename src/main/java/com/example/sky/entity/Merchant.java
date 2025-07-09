package com.example.sky.entity;

import lombok.Data;

@Data
public class Merchant extends Account{
    private Integer merchantId;
    private String merchantPic;
}
