package com.laolang.km.module.portal.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalPageController {

    @RequestMapping("/portal/error/404")
    public String index() {
        return "portal/error/404";
    }
}
