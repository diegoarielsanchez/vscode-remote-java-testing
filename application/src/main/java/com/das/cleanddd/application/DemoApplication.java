package com.das.cleanddd.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.das.cleanddd.service.MyService;

//@SpringBootApplication(scanBasePackages = "com.das.cleanddd")
@SpringBootApplication(scanBasePackages = {"com.das.cleanddd", "com.das.cleanddd.application",  "com.das.cleanddd.service", "com.das.cleanddd.domain"})
//@ComponentScan(basePackages = {"com.das.cleanddd.domain", "com.das.cleanddd.service"})
@RestController
public class DemoApplication {

  private final MyService myService;

  public DemoApplication(MyService myService) {
    this.myService = myService;
  }

  @GetMapping("/")
  public String home() {
    return myService.message();
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}