package com.adaa7.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {
    public static String UPLOADS_PATH= System.getProperty("user.dir") + "/File/";
    public static String uploadsFile(MultipartFile file) throws IOException {
        String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
        String fileName = UUID.randomUUID().toString().replace("-","")+"."+fileSuffix;
        fileDir(UPLOADS_PATH);
        File descFile = new File(UPLOADS_PATH,fileName);
        file.transferTo(descFile);
        return UPLOADS_PATH+fileName;
    }
    private static void fileDir(String url) {
        File file = new File(url);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
