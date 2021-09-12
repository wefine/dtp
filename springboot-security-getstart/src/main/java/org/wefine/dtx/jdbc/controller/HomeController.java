package org.wefine.dtx.jdbc.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @RequestMapping("/hello")
    @Secured("ROLE_USER")
    public String hello() {
        return "world";
    }
}
