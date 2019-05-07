package com.xtjnoob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = "com.xtjnoob")
@MapperScan("com.xtjnoob.dao")
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
