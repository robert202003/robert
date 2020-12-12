package org.robert.goods.api.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface OutputInterface {

    /**
     * 商品添加
     **/
    String MQ_GOODS_ADD = "robert_mq_goods_add_output";

    @Output(MQ_GOODS_ADD)
    MessageChannel goodsAdd();

}
