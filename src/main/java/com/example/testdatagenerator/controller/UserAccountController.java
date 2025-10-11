package com.example.testdatagenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAccountController {

    @GetMapping("/my-account")
    public String myAccount(Model model) {
        model.addAttribute("nickname", "백재원");
        model.addAttribute("email", "bjo3963@naver.com");

        return "my-account";
    }
}
