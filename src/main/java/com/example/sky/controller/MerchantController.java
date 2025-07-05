package com.example.sky.controller;

import org.springframework.web.bind.annotation.*;
import com.example.sky.entity.Merchant;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @PostMapping("/register")
    public String register(@RequestBody Merchant merchant) {
        System.out.println("注册商户:" + merchant);
        return "注册成功";
    }
}
