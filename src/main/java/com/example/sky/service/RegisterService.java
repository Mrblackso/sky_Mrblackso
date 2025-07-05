package com.example.sky.service;

import com.example.sky.dto.RegisterDto;
import com.example.sky.entity.User;
import com.example.sky.entity.Merchant;
import com.example.sky.exception.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sky.mapper.RegisterMapper;

@Service
public class RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    public Result registerMerchant(RegisterDto registerDto) {
        // 1. 校验手机号是否已注册
        if (registerMapper.existsMerchantByPhone(registerDto.getPhone())) {
            return Result.error("该手机号已被注册为商家");
        }
        // 2. 构建商家对象
        Merchant merchant = new Merchant();
        merchant.setPhone(registerDto.getPhone());
        merchant.setPassword(registerDto.getPassword());
        // 3. 保存到数据库
        registerMapper.insertMerchant(merchant);
        // 4. 返回结果
        return Result.success("商家注册成功");
    }

    public Result registerUser(RegisterDto registerDto) {
        // 1. 校验手机号是否已注册
        if (registerMapper.existsUserByPhone(registerDto.getPhone())) {
            return Result.error("该手机号已被注册为用户");
        }
        // 2. 构建用户对象
        User user = new User();
        user.setPhone(registerDto.getPhone());
        user.setPassword(registerDto.getPassword());
        // 3. 保存到数据库
        registerMapper.insertUser(user);
        // 4. 返回结果
        return Result.success("用户注册成功");
    }
}
