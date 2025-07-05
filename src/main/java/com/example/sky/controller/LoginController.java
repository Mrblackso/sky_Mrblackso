package com.example.sky.controller;

import com.example.sky.dto.LoginDto;
import com.example.sky.exception.Result;
import com.example.sky.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto)
    {
        System.out.println(loginDto);
        if(loginDto.getRole().equals("Merchant")){
            loginService.loginMerchant(loginDto);
        }else if(loginDto.getRole().equals("User")){
            loginService.loginUser(loginDto);
        }else{
            return Result.error("角色类型错误");
        }
        return Result.success("登录成功");
    }
}
