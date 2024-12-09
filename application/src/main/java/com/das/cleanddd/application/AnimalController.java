package com.das.cleanddd.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.das.cleanddd.service.Dog;
import com.das.cleanddd.service.Animal;

@RestController
public class AnimalController {

    Animal an = new Dog();
    @GetMapping("/animal")
    public String sayDog() {
        return an.toString();
    }
    //system.out.print(an.a);
    //an.display();
}
