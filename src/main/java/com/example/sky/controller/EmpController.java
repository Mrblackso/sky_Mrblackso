package com.example.sky.controller;

import com.example.sky.dto.SearchDto;
import com.example.sky.entity.Emp;
import com.example.sky.exception.Result;
import com.example.sky.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping("/list")
    public Result list( SearchDto searchDto){
        return empService.list(searchDto);
    }
} 