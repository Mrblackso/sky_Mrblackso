package com.example.sky.mapper;

import com.example.sky.dto.EmpDeleteDto;
import com.example.sky.dto.EmpSearchDto;
import com.example.sky.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {

    List<Emp> findByEmpId(EmpSearchDto searchDto);

    void add(Emp emp);

    void edit(Emp emp);

    void delete(EmpDeleteDto empDeleteDto);
} 