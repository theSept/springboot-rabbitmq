package com.xu.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.util.Date;

/**
 * User: 彼暗
 * Date: 2022-07-29
 * Time: 16:33
 * Versions: 1.0.0
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    // 接受消息
    @RabbitListener(queues = "QD")
    public void receiveD(Message message) throws Exception{
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到死信队列的消息：{}",new Date().toString(),msg) ;
    }
}
