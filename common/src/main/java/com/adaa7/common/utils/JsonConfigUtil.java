package com.adaa7.common.utils;

import com.adaa7.common.result.JsonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class JsonConfigUtil {
    public static String UPLOADS_PATH = "";
    public static final String JsonConfig= System.getProperty("user.dir") + "/Configuration.json";
    public static void  LoadFileDriver(){
        Path path = Paths.get(JsonConfig);
        JsonConfig jsonConfig = new JsonConfig();
        ObjectMapper objectMapper = new ObjectMapper();
        if(Files.exists(path)) {
            System.out.println("|-----------加载文件读取驱动成功----------------|");
            System.out.println("|--------------------------------------------|");
            try {
                JsonConfig config = objectMapper.readValue(new File(JsonConfig), JsonConfig.class);
                UPLOADS_PATH = config.getUPLOADS_PATH();
            } catch (Exception e) {
                throw new RuntimeException("JsonConfig配置读取失败");
            }
        }else {
            System.out.println("没有检测到Json文件正在创建"+JsonConfig);
            try {
                jsonConfig.setUPLOADS_PATH(System.getProperty("user.dir") + "/File/");
                objectMapper.writeValue(new File(JsonConfig), jsonConfig);
                System.out.println("JSON 文件 'Configuration.json' 已创建");
                JsonConfig config = objectMapper.readValue(new File(JsonConfig), JsonConfig.class);
                UPLOADS_PATH = config.getUPLOADS_PATH();
            } catch (Exception e) {
                throw new RuntimeException("JsonConfig配置创建失败");
            }
        }
    }
}
