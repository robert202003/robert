package org.robert.goods.api.controller;

import org.robert.goods.api.message.UserMessage;
import org.robert.goods.api.stream.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("token")
    public Object tree(HttpServletRequest request) {
        return "token";
    }


    @PostMapping(value = "sendUserMessage")
    public Object message(@RequestBody UserMessage message) {
        messageProducer.sendMessage(message);
        return message;
    }

}
