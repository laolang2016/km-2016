package com.laolang.km.module.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageController {

    @RequestMapping("/admin/error/404")
    public String index() {
        return "admin/error/404";
    }
}
