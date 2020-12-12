package org.robert.goods.api.stream;

import lombok.extern.slf4j.Slf4j;
import org.robert.goods.api.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/***
 * rabbitmq 消息生产者
 */
@Component
@Slf4j
@EnableBinding(value = {OutputInterface.class})
public class MessageProducer {

    @Autowired
    private OutputInterface outputInterface;

    public void sendMessage(UserMessage userMessage) {
        Message message = MessageBuilder.withPayload(userMessage).build();
        log.info("用户信息，发送消息：【{}】", userMessage);
        outputInterface.goodsAdd().send(message);
    }
}
