package com.xu.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * User: 彼暗
 * Date: 2022-07-30
 * Time: 10:32
 * Versions: 1.0.0
 */
@Slf4j
@Configuration
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate template;


    // 其他
    @PostConstruct
    public void init() {
        // 注入
        this.template.setConfirmCallback(this);
        this.template.setReturnCallback(this);
    }

    /*
     * 交换机确认回调方法
     *  1.发消息，交换机接收到了
     *   1.1 correlationData 保存回调消息的id及相关信息
     *   1.2 交换机收到消息， ack = true
     *  2.发消息 交换机接受失败 回调
     *   2.1 correlationData 保存回调消息的id及相关信息
     *   2.2 交换机收到消息， ack = false
     *   2.3 cause 失败的原因
     *
     * 发布消息成功到交换器后会触发回调
     * 配置文件开启交换机确认 ：spring.rabbitmq.publisher-confirm-type=correlated
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData == null ? "" : correlationData.getId();
        if (b) {
            log.info("交换已经成功收到id为：{}的信息", id);
        } else {
            log.info("交换机还未收到Id为：{}的消息，由于原因：{}", id, s);
        }
    }

    // 消息回退 ： 可以在当消息传递过程中不可达目的地时将消息返回给生产者
    // 配置开启回退消息 ： spring.rabbitmq.publisher-returns=true
    // 只有不可到达目的地才进行回退，如果配置了备份交换机，将会执行备份交换机，并不会执行回退
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        log.info("消息：{}，被交换机：{}回退，回退原因：{}，消息的key:{}",
                new String(message.getBody()), exchange, replyText, routingKey);
    }
}
