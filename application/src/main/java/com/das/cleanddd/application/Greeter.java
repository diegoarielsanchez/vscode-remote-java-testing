package com.das.cleanddd.application;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/v1/greet")
public class Greeter {

    @GetMapping("/hello")
    public String greet(String name) {
        return "Hello " + name;
    }

    @GetMapping("/hellorecord")
    public String greetwithrecord(@RequestBody Greetting greetting) {
        return "Hello " + greetting;
    }
}
