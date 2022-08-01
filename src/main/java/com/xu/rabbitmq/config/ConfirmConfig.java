package com.xu.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * User: 彼暗
 * Date: 2022-07-30
 * Time: 9:31
 * Versions: 1.0.0
 * 发布确认（高级）
 */
@Configuration
public class ConfirmConfig {
    // 队列名
    public static final String DELAYED_QUEUE_NAME = "confirm.queue";
    // 交换机名
    public static final String DELAYED_EXCHANGE_NAME = "confirm.exchange";
    // routingKey
    public static final String DELAYED_ROUTING_KEY = "confirm.routingkey";


    // 备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    // 备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    // 警告队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";

    // 确认交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(DELAYED_EXCHANGE_NAME)
                .durable(true) // 持久化
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME) // 指定备份交换机
                .build();
    }


    // 队列
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(DELAYED_QUEUE_NAME).build();
    }

    // 交换机绑定队列
    @Bean
    public Binding confirmQueueBindingConfirmExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                                      @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(DELAYED_ROUTING_KEY);
    }


    // 备份交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE_NAME).build();
    }

    // 备份队列
    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    // 报警队列
    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    // 备份队列绑定备份交换机
    @Bean
    public Binding backupQueueBindingBackupExchange(@Qualifier("backupQueue") Queue backupQueue,
                                                    @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    // 报警队列绑定备份交换机
    @Bean
    public Binding warningQueueBindingBackupExchange(@Qualifier("warningQueue") Queue warningQueue,
                                                     @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }


    // 声明惰性队列
    @Bean
    public Queue lazyQueue() {
        // durable("队列名")
        return QueueBuilder.durable("lazy.queue")
                // 开启x-queue-mode: lazy
                .lazy()
                .build();
    }
}
