package org.robert.rabbit.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

/***
 * rabbitmq 消息生产者
 */
@Component
@Slf4j
@EnableBinding(value = {OutputInterface.class})
public class MessageProducer {
}
