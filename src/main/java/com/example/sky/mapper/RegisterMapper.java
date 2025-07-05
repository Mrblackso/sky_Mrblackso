package com.example.sky.mapper;

import com.example.sky.entity.User;
import com.example.sky.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegisterMapper {
    // 判断手机号是否已注册为商家
    boolean existsMerchantByPhone(@Param("phone") String phone);

    // 判断手机号是否已注册为用户
    boolean existsUserByPhone(@Param("phone") String phone);

    // 插入商家
    void insertMerchant(Merchant merchant);

    // 插入用户
    void insertUser(User user);
}