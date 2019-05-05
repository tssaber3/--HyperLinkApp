package com.us.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ServletComponentScan//在springboot启动时会扫描WebServlet  并将其实例化
@MapperScan("com.us.demo.mapper")//扫描mapper包
//@EnableCaching
public class HyperLinkAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyperLinkAppApplication.class, args);
    }

}
