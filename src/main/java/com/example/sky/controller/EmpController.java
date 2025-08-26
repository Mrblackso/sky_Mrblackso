package com.example.sky.controller;

import com.example.sky.dto.EmpSearchDto;
import com.example.sky.dto.EmpDeleteDto;
import com.example.sky.entity.Emp;
import com.example.sky.exception.Result;
import com.example.sky.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping("/list")
    public Result list( EmpSearchDto searchDto){
        return empService.list(searchDto);
    }
    @PostMapping("/add")
    public Result add(@RequestBody Emp emp){
        return empService.add(emp);
    }
    @PostMapping("/edit")
    public Result edit(@RequestBody Emp emp){
        return empService.edit(emp);
    }
    @DeleteMapping("/delete")
    public Result delete(@RequestBody EmpDeleteDto empDeleteDto){
        return empService.delete(empDeleteDto);
    }
} 