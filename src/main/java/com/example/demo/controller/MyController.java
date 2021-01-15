package com.example.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping(value = "/hello")
    @Secured({"ROLE_TEST"})
    public String hello() {
        return "hello";
    }
}
