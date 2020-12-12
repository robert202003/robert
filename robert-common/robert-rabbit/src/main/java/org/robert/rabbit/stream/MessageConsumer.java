package org.robert.rabbit.stream;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.robert.rabbit.message.UserMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableBinding(value = {OutputInterface.class})
public class MessageConsumer {

    @StreamListener(value = InputInterface.MQ_GOODS_ADD)
    private void userMessage(UserMessage userMessage) {
        log.info("收到用户信息：{}", JSON.toJSONString(userMessage));
    }

}
