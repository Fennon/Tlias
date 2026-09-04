package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

   /*
   存储到磁盘
   @PostMapping("/upload")
    public Result upload(String name,Integer age ,MultipartFile file) throws IOException {
        log.info("接收参数：{}，{}，{}",name,age,file);

        String fileName=file.getOriginalFilename();

        String extension=fileName.substring(fileName.lastIndexOf("."));
        String newFileName= UUID.randomUUID().toString()+extension;

        file.transferTo(new File("E:\\Programming\\App\\IDEA\\Code\\HeiMa-javaweb\\web-ai-project02\\"+newFileName));
        return Result.success();
    }*/

    @Autowired
    AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传；{}", file.getOriginalFilename());

        String url= aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());

        log.info("文件上传到oss,url:{}",url);

        return Result.success(url);

    }


}
