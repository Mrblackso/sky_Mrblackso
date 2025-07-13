package com.example.sky.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtils {
    
    // 图片存储路径
    private static final String UPLOAD_PATH = "uploads/images/";
    
    // 允许的图片类型
    private static final String[] ALLOWED_IMAGE_TYPES = {
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp"
    };
    
    /**
     * 上传图片文件
     * @param file 上传的文件
     * @return 文件访问URL
     */
    public static String uploadImage(MultipartFile file) throws IOException {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传的文件不能为空");
        }
        
        // 检查文件类型
        String contentType = file.getContentType();
        if (!isValidImageType(contentType)) {
            throw new IllegalArgumentException("不支持的文件类型，只支持JPG、PNG、GIF、BMP格式");
        }
        
        // 检查文件大小（限制为5MB）
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("文件大小不能超过5MB");
        }
        
        // 创建上传目录
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String filename = UUID.randomUUID().toString() + extension;
        
        // 保存文件
        File destFile = new File(uploadDir, filename);
        file.transferTo(destFile);
        
        // 生成缩略图
        generateThumbnail(destFile);
        
        // 返回文件访问路径
        return "/images/" + filename;
    }
    
    /**
     * 检查是否为有效的图片类型
     */
    private static boolean isValidImageType(String contentType) {
        if (contentType == null) {
            return false;
        }
        for (String type : ALLOWED_IMAGE_TYPES) {
            if (type.equals(contentType)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获取文件扩展名
     */
    private static String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
    
    /**
     * 生成缩略图
     */
    private static void generateThumbnail(File originalFile) throws IOException {
        String thumbnailPath = UPLOAD_PATH + "thumbnails/";
        File thumbnailDir = new File(thumbnailPath);
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }
        
        String thumbnailFilename = "thumb_" + originalFile.getName();
        File thumbnailFile = new File(thumbnailDir, thumbnailFilename);
        
        // 生成200x200的缩略图
        Thumbnails.of(originalFile)
                .size(200, 200)
                .keepAspectRatio(true)
                .toFile(thumbnailFile);
    }
    
    /**
     * 删除图片文件
     */
    public static boolean deleteImage(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }
        
        // 移除URL前缀
        String filename = imageUrl.replace("/images/", "");
        File imageFile = new File(UPLOAD_PATH + filename);
        File thumbnailFile = new File(UPLOAD_PATH + "thumbnails/thumb_" + filename);
        
        boolean deleted = false;
        if (imageFile.exists()) {
            deleted = imageFile.delete();
        }
        
        if (thumbnailFile.exists()) {
            thumbnailFile.delete();
        }
        
        return deleted;
    }
} 