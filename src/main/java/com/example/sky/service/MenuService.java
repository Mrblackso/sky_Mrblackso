package com.example.sky.service;

import com.example.sky.dto.MenuDeleteDto;
import com.example.sky.dto.MenuSearchDto;
import com.example.sky.entity.Menu;
import com.example.sky.exception.Result;
import com.example.sky.mapper.MenuMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public Result list(MenuSearchDto searchDto) {
        // 开启分页
        PageHelper.startPage(searchDto.getPageNum(), searchDto.getPageSize());

        // 查询数据
        List<Menu> menuList = menuMapper.findByMenuId(searchDto);

        // 返回分页信息
        PageInfo<Menu> pageInfo = new PageInfo<>(menuList);
        return Result.success(pageInfo);
    }

    public Result add(Menu menu) {
        menuMapper.add(menu);
        return Result.success("添加员工成功");
    }

    public Result edit(Menu menu) {
        menuMapper.edit(menu);
        return Result.success("修改员工成功");
    }

    public Result delete(@RequestBody MenuDeleteDto menuDeleteDto) {
        menuMapper.delete(menuDeleteDto);
        return Result.success("删除员工成功");
    }
}