package com.example.sky.mapper;

import com.example.sky.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    
    /**
     * 查询所有菜品
     */
    List<Menu> selectAllMenus();
    
    /**
     * 根据商家ID查询菜品
     */
    List<Menu> selectMenusByMerchantId(@Param("merchantId") Integer merchantId);
    
    /**
     * 根据ID查询菜品
     */
    Menu selectMenuById(@Param("menuId") Integer menuId);
    
    /**
     * 插入新菜品
     */
    int insertMenu(Menu menu);
    
    /**
     * 更新菜品信息
     */
    int updateMenu(Menu menu);
    
    /**
     * 删除菜品
     */
    int deleteMenuById(@Param("menuId") Integer menuId);
    
    /**
     * 根据菜品名称模糊查询
     */
    List<Menu> selectMenusByName(@Param("menuName") String menuName);
} 