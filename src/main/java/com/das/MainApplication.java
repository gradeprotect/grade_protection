package com.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
/**
 * @author 许文滨
 * @date 2020-8-17
 */
public class MainApplication extends SpringBootServletInitializer {
    public static void main(String[] args){
        SpringApplication.run(MainApplication.class,args);
    }

    /**
     * 打包成war包部署在tomcat中
     * 1. extends SpringBootServletInitializer
     * 2. SpringApplicationBuilder configure(SpringApplicationBuilder builder) 重写
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(MainApplication.class);
    }

}
