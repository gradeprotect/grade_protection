package com.das;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan({"com.das.controller","com.das.service","com.das.mapper"})
/**
 * @author 许文滨
 * @date 2020-8-17
 */
public class MainApplication {

    public static void main(String[] args){
        SpringApplication.run(MainApplication.class,args);
    }
}
