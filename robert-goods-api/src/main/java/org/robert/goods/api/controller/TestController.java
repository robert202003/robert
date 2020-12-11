package org.robert.goods.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/test/")
public class TestController {

    @PostMapping("token")
    public Object tree(HttpServletRequest request) {
        return "fa";
    }

    @PostConstruct
    public void init() {
        System.out.println("aa");
    }
}
