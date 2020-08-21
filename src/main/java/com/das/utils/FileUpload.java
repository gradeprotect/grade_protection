package com.das.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tim
 */
public class FileUpload {
    public static String upload(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return "附件为空";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf("."));
        String newFileName = sdf.format(new Date()) + (int) (Math.random() * 100) + suffixName;
        String substring = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
        String filePath = substring+"upload/";
        String path = filePath+newFileName;
        File dest = new File(path);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return path;
    }
}
