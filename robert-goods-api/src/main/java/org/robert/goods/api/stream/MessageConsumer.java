package org.robert.goods.api.stream;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.robert.goods.api.message.UserMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@EnableBinding(value = {InputInterface.class})
public class MessageConsumer {

    @StreamListener(value = InputInterface.MQ_GOODS_ADD)
    public void userMessage(@Payload Message<UserMessage> message) {
        UserMessage userMessage = message.getPayload();

        log.info("收到用户信息：{}", JSON.toJSONString(userMessage));
        System.out.println(userMessage.toString());
    }

    @PostConstruct
    public void init(){
        System.out.println("a");
    }

}
