package com.simple.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/15 3:22 下午
 * @Description：
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.producer.topic}", consumerGroup = "${rocketmq.producer.group}")
public class SpringBootRocketmqConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg){
        log.info("==========收到消息:" + msg);
    }
}