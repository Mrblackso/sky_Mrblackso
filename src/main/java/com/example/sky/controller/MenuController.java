package com.example.sky.controller;

import com.example.sky.entity.Menu;
import com.example.sky.exception.Result;
import com.example.sky.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    /**
     * 获取所有菜品
     */
    @GetMapping("/list")
    public Result getAllMenus() {
        try {
            List<Menu> menus = menuService.getAllMenus();
            return Result.success(menus);
        } catch (Exception e) {
            return Result.error("获取菜品列表失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据商家ID获取菜品
     */
    @GetMapping("/merchant/{merchantId}")
    public Result getMenusByMerchantId(@PathVariable Integer merchantId) {
        try {
            List<Menu> menus = menuService.getMenusByMerchantId(merchantId);
            return Result.success(menus);
        } catch (Exception e) {
            return Result.error("获取商家菜品失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据ID获取菜品详情
     */
    @GetMapping("/{menuId}")
    public Result getMenuById(@PathVariable Integer menuId) {
        try {
            Menu menu = menuService.getMenuById(menuId);
            if (menu != null) {
                return Result.success(menu);
            } else {
                return Result.error("菜品不存在");
            }
        } catch (Exception e) {
            return Result.error("获取菜品详情失败：" + e.getMessage());
        }
    }
    
    /**
     * 添加菜品（包含图片上传）
     */
    @PostMapping("/add")
    public Result addMenu(@RequestParam("menuName") String menuName,
                          @RequestParam("menuDescription") String menuDescription,
                          @RequestParam("menuPrice") Double menuPrice,
                          @RequestParam("merchantId") Integer merchantId,
                          @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            Menu menu = new Menu();
            menu.setMenuName(menuName);
            menu.setMenuDescription(menuDescription);
            menu.setMenuPrice(menuPrice);
            menu.setMerchantId(merchantId);
            
            boolean success = menuService.addMenu(menu, imageFile);
            if (success) {
                return Result.success("菜品添加成功");
            } else {
                return Result.error("菜品添加失败");
            }
        } catch (Exception e) {
            return Result.error("菜品添加失败：" + e.getMessage());
        }
    }
    
    /**
     * 更新菜品信息
     */
    @PostMapping("/update")
    public Result updateMenu(@RequestParam("menuId") Integer menuId,
                             @RequestParam("menuName") String menuName,
                             @RequestParam("menuDescription") String menuDescription,
                             @RequestParam("menuPrice") Double menuPrice,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            Menu menu = new Menu();
            menu.setMenuId(menuId);
            menu.setMenuName(menuName);
            menu.setMenuDescription(menuDescription);
            menu.setMenuPrice(menuPrice);
            
            boolean success = menuService.updateMenu(menu, imageFile);
            if (success) {
                return Result.success("菜品更新成功");
            } else {
                return Result.error("菜品更新失败");
            }
        } catch (Exception e) {
            return Result.error("菜品更新失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除菜品
     */
    @DeleteMapping("/{menuId}")
    public Result deleteMenu(@PathVariable Integer menuId) {
        try {
            boolean success = menuService.deleteMenu(menuId);
            if (success) {
                return Result.success("菜品删除成功");
            } else {
                return Result.error("菜品删除失败");
            }
        } catch (Exception e) {
            return Result.error("菜品删除失败：" + e.getMessage());
        }
    }
    
    /**
     * 搜索菜品
     */
    @GetMapping("/search")
    public Result searchMenus(@RequestParam("menuName") String menuName) {
        try {
            List<Menu> menus = menuService.searchMenusByName(menuName);
            return Result.success(menus);
        } catch (Exception e) {
            return Result.error("搜索菜品失败：" + e.getMessage());
        }
    }
    
    /**
     * 仅上传图片（返回图片URL）
     */
    @PostMapping("/upload-image")
    public Result uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String imageUrl = menuService.uploadImageOnly(imageFile);
            return Result.success(imageUrl);
        } catch (Exception e) {
            return Result.error("图片上传失败：" + e.getMessage());
        }
    }
}
