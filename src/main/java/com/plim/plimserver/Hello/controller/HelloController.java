package com.plim.plimserver.Hello.controller;

import com.plim.plimserver.Hello.domain.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Hello getHello(){

        return Hello
                .builder()
                .name("PLIM")
                .age(24)
                .say("Hello Test Controller!! - PLIM")
                .build();
    }
}
