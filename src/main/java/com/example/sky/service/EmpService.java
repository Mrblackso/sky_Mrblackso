package com.example.sky.service;

import com.example.sky.dto.SearchDto;
import com.example.sky.entity.Emp;
import com.example.sky.exception.Result;
import com.example.sky.mapper.EmpMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpService {
    @Autowired
    private EmpMapper empMapper;

    public Result list(SearchDto searchDto) {
        // 开启分页
        PageHelper.startPage(searchDto.getPageNum(), searchDto.getPageSize());

        // 查询数据
        List<Emp> empList = empMapper.findByEmpId(searchDto);

        // 返回分页信息
        PageInfo<Emp> pageInfo = new PageInfo<>(empList);
        return Result.success(pageInfo);
    }
} 