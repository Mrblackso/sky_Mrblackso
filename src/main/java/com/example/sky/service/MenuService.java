package com.example.sky.service;

import com.example.sky.entity.Menu;
import com.example.sky.mapper.MenuMapper;
import com.example.sky.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MenuService {
    
    @Autowired
    private MenuMapper menuMapper;
    
    /**
     * 获取所有菜品
     */
    public List<Menu> getAllMenus() {
        return menuMapper.selectAllMenus();
    }
    
    /**
     * 根据商家ID获取菜品
     */
    public List<Menu> getMenusByMerchantId(Integer merchantId) {
        return menuMapper.selectMenusByMerchantId(merchantId);
    }
    
    /**
     * 根据ID获取菜品
     */
    public Menu getMenuById(Integer menuId) {
        return menuMapper.selectMenuById(menuId);
    }
    
    /**
     * 添加菜品（包含图片上传）
     */
    public boolean addMenu(Menu menu, MultipartFile imageFile) {
        try {
            // 上传图片
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = FileUploadUtils.uploadImage(imageFile);
                menu.setMenuPic(imageUrl);
            }
            
            // 保存菜品信息
            int result = menuMapper.insertMenu(menu);
            return result > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 更新菜品信息
     */
    public boolean updateMenu(Menu menu, MultipartFile imageFile) {
        try {
            // 如果上传了新图片
            if (imageFile != null && !imageFile.isEmpty()) {
                // 删除旧图片
                Menu oldMenu = menuMapper.selectMenuById(menu.getMenuId());
                if (oldMenu != null && oldMenu.getMenuPic() != null) {
                    FileUploadUtils.deleteImage(oldMenu.getMenuPic());
                }
                
                // 上传新图片
                String imageUrl = FileUploadUtils.uploadImage(imageFile);
                menu.setMenuPic(imageUrl);
            }
            
            // 更新菜品信息
            int result = menuMapper.updateMenu(menu);
            return result > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除菜品
     */
    public boolean deleteMenu(Integer menuId) {
        try {
            // 获取菜品信息
            Menu menu = menuMapper.selectMenuById(menuId);
            if (menu != null && menu.getMenuPic() != null) {
                // 删除图片文件
                FileUploadUtils.deleteImage(menu.getMenuPic());
            }
            
            // 删除数据库记录
            int result = menuMapper.deleteMenuById(menuId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据名称搜索菜品
     */
    public List<Menu> searchMenusByName(String menuName) {
        return menuMapper.selectMenusByName(menuName);
    }
    
    /**
     * 仅上传图片（不保存菜品信息）
     */
    public String uploadImageOnly(MultipartFile imageFile) throws IOException {
        return FileUploadUtils.uploadImage(imageFile);
    }
} 