package com.example.sky.service;

import com.example.sky.dto.SearchDto;
import com.example.sky.dto.EmpDeleteDto;
import com.example.sky.entity.Emp;
import com.example.sky.exception.Result;
import com.example.sky.mapper.EmpMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Result add(Emp emp) {
        empMapper.add(emp);
        return Result.success("添加员工成功");
    }

    public Result edit(Emp emp) {
        empMapper.edit(emp);
        return Result.success("修改员工成功");
    }

    public Result delete(@RequestBody EmpDeleteDto empDeleteDto) {
        empMapper.delete(empDeleteDto);
        return Result.success("删除员工成功");
    }
}