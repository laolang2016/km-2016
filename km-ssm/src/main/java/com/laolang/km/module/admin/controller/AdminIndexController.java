package com.laolang.km.module.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminIndexController {

    @RequestMapping("/admin")
    public String index(Model model) {
        model.addAttribute("msg", "km 系统");
        return "admin/index";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("msg", "km 系统");
        return "admin/dashboard";
    }
}
