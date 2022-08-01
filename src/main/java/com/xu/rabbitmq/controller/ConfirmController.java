package com.xu.rabbitmq.controller;

import com.xu.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: 彼暗
 * Date: 2022-07-30
 * Time: 9:38
 * Versions: 1.0.0
 * --------------------发布确认（高级）------------------------
 *   开始发消息
 */
@RestController
@Slf4j
@RequestMapping("/confirm")
public class ConfirmController {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        CorrelationData correlationData1 = new CorrelationData("11");
        rabbitTemplate.convertAndSend(ConfirmConfig.DELAYED_EXCHANGE_NAME,ConfirmConfig.DELAYED_ROUTING_KEY,message+"key1",correlationData1);
        log.info("发送消息内容为：{}",message+"key1");

        CorrelationData correlationData2 = new CorrelationData("22");
        rabbitTemplate.convertAndSend(ConfirmConfig.DELAYED_EXCHANGE_NAME,ConfirmConfig.DELAYED_ROUTING_KEY+"2233",message+"key2",correlationData2);
        log.info("发送消息内容为：{}",message);
    }

}

