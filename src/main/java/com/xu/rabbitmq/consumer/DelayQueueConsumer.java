package com.xu.rabbitmq.consumer;

import com.xu.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import java.util.Date;

/**
 * User: 彼暗
 * Date: 2022-07-29
 * Time: 22:40
 * Versions: 1.0.0
 * 消费者，基于插件的延迟消息
 */
@Slf4j
@Configuration
public class DelayQueueConsumer {

    @RabbitListener(queues = DelayedQueueConfig.QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延迟队列消息：{}",new Date().toString(),msg);
    }

}
