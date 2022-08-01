package com.xu.rabbitmq.controller;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * User: 彼暗
 * Date: 2022-07-29
 * Time: 16:22
 * Versions: 1.0.0
 *  生产者
 */
@Slf4j
@RestController
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 开始发消息
    @GetMapping("/sendMsg/{msg}")
    public void sendMsg(@PathVariable() String msg) {
        log.info("当前时间：{}，发送一条信息给两个TTL队列:{}", new Date().toString(), msg);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl10秒的队列：" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl40秒的队列：" + msg);
    }

    // 发送指定ttl的信息
    @GetMapping("/sendExpirationMSg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,
                        @PathVariable String ttlTime){
        log.info("当前时间：{}，发送时长{}毫秒,TTL信息给队列QC:{}", new Date().toString(), message,ttlTime);
        // 发送消息
        rabbitTemplate.convertAndSend("X","XC",message,message1 -> {
            // 发送消息的延迟时长
            message1.getMessageProperties().setExpiration(ttlTime);
            return message1;
        });
    }


    // 开始发送消息，基于插件
    @GetMapping("/sendDelayMSg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,
                        @PathVariable Integer ttlTime) {
        log.info("当前时间：{}，发送时长{}毫秒的信息给队列delayed.queue:{}", new Date().toString(), message, ttlTime);

        rabbitTemplate.convertAndSend("delayed.exchange","delayed.routingkey",message,message1 -> {
            // 设置ttl时间
            message1.getMessageProperties().setDelay(ttlTime);
            return message1;
        });
    }




}
