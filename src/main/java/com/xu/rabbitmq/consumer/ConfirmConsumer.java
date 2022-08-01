package com.xu.rabbitmq.consumer;

import com.xu.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * User: 彼暗
 * Date: 2022-07-30
 * Time: 9:42
 * Versions: 1.0.0
 * 发布确认（高级） 接收消息，消费者
 */
@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.DELAYED_QUEUE_NAME)
    public void receiveConfirmMessage(Message message) throws Exception{
        String msg = new String(message.getBody());
        log.info("成功接受到队列ConfirmQueue的消息：{}",msg);
    }

}
