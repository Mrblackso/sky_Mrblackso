package com.example.sky.mapper;

import com.example.sky.dto.MenuDeleteDto;
import com.example.sky.dto.MenuSearchDto;
import com.example.sky.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<Menu> findByMenuId(MenuSearchDto searchDto);

    void add(Menu menu);

    void edit(Menu menu);

    void delete(MenuDeleteDto menuDeleteDto);
}