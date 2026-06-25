package com.yvl.vorstu.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/base")
public class BaseController {

    @GetMapping("check")
    public String greetJava() {
        return "Hello world " + new Date();
    }
}
