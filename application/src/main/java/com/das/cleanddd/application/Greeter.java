package com.das.cleanddd.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeter {

    @GetMapping("/greet")
    public String greet(String name) {
        return "Hello " + name;
    }

}