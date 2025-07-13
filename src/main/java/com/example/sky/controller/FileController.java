package com.example.sky.controller;

import com.example.sky.exception.Result;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@RestController
@RequestMapping("file")
public class FileController {

    // 获取项目根目录
    private static final String ROOT = System.getProperty("user.dir") + "/Files/";

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        synchronized (FileController.class) {
            try {
                // 生成唯一文件名
                String flag = System.currentTimeMillis() + "";
                String fileName = flag + file.getOriginalFilename();

                // 构建文件保存路径
                Path filePath = Paths.get(ROOT, fileName);

                // 如果目录不存在，则创建
                Path rootDir = Paths.get(ROOT);
                if (!Files.exists(rootDir)) {
                    Files.createDirectories(rootDir);
                }

                // 保存上传的文件
                file.transferTo(filePath);

                // 返回成功结果
                return Result.success(fileName);
            } catch (IOException e) {
                // 记录异常日志并返回错误信息
                System.err.println("文件上传失败：" + e.getMessage());
                return Result.error("文件上传失败");
            }
        }
    }


        @GetMapping("/download")
        public ResponseEntity<Resource> download(String filename) {
            try {
                Path filePath = Paths.get(ROOT, filename).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                if (resource.exists()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);
                } else {
                    throw new RuntimeException("文件不存在");
                }
            } catch (MalformedURLException | RuntimeException e) {
                System.err.println("文件下载失败：" + e.getMessage());
                return ResponseEntity.badRequest().build();
            }
        }


}
