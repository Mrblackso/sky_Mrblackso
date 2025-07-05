package com.example.sky.controller;

import com.example.sky.dto.RegisterDto;
import com.example.sky.exception.Result;
import com.example.sky.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto) {
//        System.out.println(registerDto);
        if ("Merchant".equals(registerDto.getRole())) {
            return registerService.registerMerchant(registerDto);
        } else if ("User".equals(registerDto.getRole())) {
            return registerService.registerUser(registerDto);
        } else {
            return Result.error("角色类型错误");
        }

        //return Result.success("注册成功");
    }
}
