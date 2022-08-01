package com.xu.rabbitmq.consumer;

import com.xu.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * User: 彼暗
 * Date: 2022-07-30
 * Time: 19:50
 * Versions: 1.0.0
 * 报警消费者
 */
@Slf4j
@Component
public class WaringConsumer {

    // 消费报警队列信息
    @RabbitListener(queues = ConfirmConfig.BACKUP_QUEUE_NAME)
    public void receiveWarningMsg(Message message){
        String msg = new String(message.getBody());
        log.info("报警发现不可路由消息:{}",msg);
    }
}
