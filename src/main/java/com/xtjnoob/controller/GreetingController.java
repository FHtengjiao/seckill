package com.xtjnoob.controller;

import com.xtjnoob.bean.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

//    @GetMapping("/greeting/{name}")
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
