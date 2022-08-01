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
 * Time: 15:39
 * Versions: 1.0.0
 */
@Configuration
public class TtlQueueConfig {

    // 普通交换机名称
    public static final String X_EXCHANGE = "X";
    // 死信交换机名称
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    // 普通队列
    public static final String XA_QUEUE = "QA";
    public static final String XB_QUEUE = "QB";
    // 死信队列
    public static final String YD_DEAD_QUEUE = "QD";

    // 声明普通队列
    public static final String QC_QUEUE = "QC";


    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> map = new HashMap<>();
        // 设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        // 设置死信 RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QC_QUEUE).withArguments(map).build();
    }
    // 队列QC 绑定 交换机X
    @Bean
    public  Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                   @Qualifier("xExchange") DirectExchange xExchange){
      return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }



    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    // 声明QA队列
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> map = new HashMap<>();

        // 设置死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 设置死信RoutingKey
        map.put("x-dead-letter-routing-key", "YD");
        // 过期时间
        map.put("x-message-ttl", 10000);

        return QueueBuilder.durable(XA_QUEUE).withArguments(map).build();
    }

    // 声明QB队列
    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> map = new HashMap<>();

        // 设置死信交换机
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 设置死信RoutingKey
        map.put("x-dead-letter-routing-key", "YD");
        // 过期时间
        map.put("x-message-ttl", 40000);

        return QueueBuilder.durable(XB_QUEUE).withArguments(map).build();
    }


    // 死信队列
    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(YD_DEAD_QUEUE).build();
    }

    // 队列QA绑定X交换机
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    // 队列QB绑定X交换机
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    // 队列QB绑定Y交换机
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }



}
