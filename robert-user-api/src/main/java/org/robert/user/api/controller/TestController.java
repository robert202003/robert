package org.robert.user.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.robert.core.base.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @RequestMapping("/test")
    public R test() {
        log.info("TEST......");
        return R.ok();
    }
}
