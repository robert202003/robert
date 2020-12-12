package org.robert.goods.api.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface InputInterface {

    String MQ_GOODS_ADD = "robert_mq_goods_add_input";

    /**
     * 商品添加
     * @return
     */
    @Input(MQ_GOODS_ADD)
    SubscribableChannel goodsAdd();


}
