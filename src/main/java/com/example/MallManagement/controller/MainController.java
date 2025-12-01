package com.example.MallManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody; // <--- THIS WAS MISSING

@Controller
public class MainController {

    @GetMapping("/")
    public String homeMenu() {
        return "index";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Die Anwendung funktioniert!";
    }
}