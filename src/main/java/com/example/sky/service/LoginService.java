package com.example.sky.service;

import com.example.sky.dto.LoginDto;
import com.example.sky.entity.Merchant;
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
        
        // 获取商户密码进行验证
        String storedPassword = loginMapper.findMerchantPassword(loginDto.getPhone());
        if (!Objects.equals(storedPassword, loginDto.getPassword())) {
            return Result.error("密码错误");
        }

        // 获取商户信息
        Merchant merchant = loginMapper.findMerchantByPhone(loginDto.getPhone());
        return Result.success(merchant);
    }

    public Result loginUser(LoginDto loginDto) {
        if (!loginMapper.existsUserByPhone(loginDto.getPhone())) {
            return Result.error("未知账号");
        }
        
        // 获取用户密码进行验证
        String storedPassword = loginMapper.findUserPassword(loginDto.getPhone());
        if (!Objects.equals(storedPassword, loginDto.getPassword())) {
            return Result.error("密码错误");
        }
        
        return Result.success("登录成功");
    }

}
