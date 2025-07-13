package com.example.sky.dto;

import lombok.Data;

@Data
public class MenuDto {
    private Integer menuId;
    private String menuName;
    private String menuPic;
    private String menuDescription;
    private Double menuPrice;
    private Integer merchantId; // 关联的商家ID
} 