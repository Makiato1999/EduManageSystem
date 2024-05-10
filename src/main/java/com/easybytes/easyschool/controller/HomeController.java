package com.easybytes.easyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    // routing
    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage() {
        //model.addAttribute("username", "Shawn Xie");
        return "home.html";
    }
}
