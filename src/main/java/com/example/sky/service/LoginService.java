package com.example.sky.service;

import com.example.sky.dto.LoginDto;
import com.example.sky.exception.Result;
import com.example.sky.mapper.LoginMapper;
import com.example.sky.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;


    public Result loginMerchant(LoginDto loginDto) {
        if (!loginMapper.existsMerchantByPhone(loginDto.getPhone())) {
            return Result.error("未知账号");
        }
        if(!Objects.equals(loginMapper.findMerchantPassword(), loginDto.getPassword())){
            return Result.error("密码错误");
        }

        return Result.success("登录成功");
    }

    public Result loginUser(LoginDto loginDto) {

        if (!loginMapper.existsUserByPhone(loginDto.getPhone())) {
            return Result.error("未知账号");
        }
        if(!Objects.equals(loginMapper.findUserPassword(), loginDto.getPassword())){
            return Result.error("密码错误");
        }
        return Result.success("登录成功");
    }

}
