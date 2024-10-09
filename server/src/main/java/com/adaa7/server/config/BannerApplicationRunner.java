package com.adaa7.server.config;
import com.adaa7.common.utils.JsonConfigUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BannerApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args){
        System.out.println("|--------------------------------------------|");
        System.out.println("|-----------开始加载文件读取驱动----------------|");
        System.out.println("|--------------------------------------------|");
        JsonConfigUtil.LoadFileDriver();
    }
}
