package com.xu.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * User: 彼暗
 * Date: 2022-07-29
 * Time: 21:37
 * Versions: 1.0.0
 * 延迟队列配置类
 */
@Configuration
public class DelayedQueueConfig {

    // 交换机名
    public static final String EXCHANGE_NAME = "delayed.exchange";
    // 队列名
    public static final String QUEUE_NAME = "delayed.queue";
    // 交换机路由key
    public static final String Routing_KEY = "delayed.routingkey";


    // 队列
    @Bean("queueDelayed")
    public Queue queueDelayed() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    // 自定义交换机 这里定义的是一个延迟交换机
    @Bean("exchangeDelayed")
    public CustomExchange exchangeDelayed() {

        Map<String, Object> map = new HashMap<>();
        // 自定义交换机的类型
        map.put("x-delayed-type", "direct");
        /*
         * 1.交换机的名称
         * 2.交换机的类型
         * 3.是否需要持久化
         * 4.是否自动删除
         * 5.其他参数
         */
        return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, map);
    }

    // 队列绑定交换机
    @Bean
    public Binding queueDBindingD(@Qualifier("queueDelayed") Queue queue,
                                  @Qualifier("exchangeDelayed") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Routing_KEY).noargs();
    }

}
