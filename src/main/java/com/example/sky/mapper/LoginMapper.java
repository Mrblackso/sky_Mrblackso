package com.example.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

    /**
     * 检查是否存在指定手机号的商户
     * @param phone 商户手机号
     * @return 存在返回true，否则返回false
     */
    boolean existsMerchantByPhone(@Param("phone") String phone);

    /**
     * 获取商户的密码
     * @return 商户的密码
     */
    String findMerchantPassword();

    /**
     * 检查是否存在指定手机号的用户
     * @param phone 用户手机号
     * @return 存在返回true，否则返回false
     */
    boolean existsUserByPhone(@Param("phone") String phone);

    /**
     * 获取用户的密码
     * @return 用户的密码
     */
    String findUserPassword();
}
