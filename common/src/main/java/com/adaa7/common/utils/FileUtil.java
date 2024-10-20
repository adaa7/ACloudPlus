package com.adaa7.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {
    public static String uploadsFile(MultipartFile file) throws IOException {
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
        String fileName = UUID.randomUUID().toString().replace("-","")+"."+fileSuffix;
        fileDir(JsonConfigUtil.UPLOADS_PATH);
        File descFile = new File(JsonConfigUtil.UPLOADS_PATH,fileName);
        file.transferTo(descFile);
        return JsonConfigUtil.UPLOADS_PATH+fileName;
    }
    public static String uploadsFileImager(MultipartFile file) throws IOException {
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
        String fileName = UUID.randomUUID().toString().replace("-","")+"."+fileSuffix;
        fileDir(JsonConfigUtil.UPLOADS_PATH+"Images/");
        File descFile = new File(JsonConfigUtil.UPLOADS_PATH+"Images/",fileName);
        file.transferTo(descFile);
        return JsonConfigUtil.UPLOADS_PATH+"Images/"+fileName;
    }
    private static void fileDir(String url) {
        File file = new File(url);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


}
