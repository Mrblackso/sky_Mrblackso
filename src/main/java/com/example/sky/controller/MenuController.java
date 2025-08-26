package com.example.sky.controller;

import com.example.sky.dto.MenuSearchDto;
import com.example.sky.dto.MenuDeleteDto;
import com.example.sky.entity.Menu;
import com.example.sky.exception.Result;
import com.example.sky.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Result list( MenuSearchDto searchDto){
        return menuService.list(searchDto);
    }
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        return menuService.add(menu);
    }
    @PostMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        return menuService.edit(menu);
    }
    @DeleteMapping("/delete")
    public Result delete(@RequestBody MenuDeleteDto menuDeleteDto){
        return menuService.delete(menuDeleteDto);
    }
} 