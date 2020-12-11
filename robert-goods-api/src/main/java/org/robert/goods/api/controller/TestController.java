package org.robert.goods.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/api/test/")
public class TestController {

    @PostMapping("token")
    public Object tree(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        return headerNames;
    }
}
