package com.finalproject.storeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("home")
    public String index(Map<String, Object> model) {
        model.put("title", "Home");

        return "home";
    }

}
