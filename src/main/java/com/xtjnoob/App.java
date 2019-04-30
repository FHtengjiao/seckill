package com.xtjnoob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
