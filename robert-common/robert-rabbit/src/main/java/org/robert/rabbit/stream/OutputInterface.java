package org.robert.rabbit.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface OutputInterface {

    /**
     * 商品添加
     **/
    String MQ_GOODS_ADD = "mq_goods_add-output";

    @Output(MQ_GOODS_ADD)
    MessageChannel goodsAdd();

}
