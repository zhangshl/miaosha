package com.simple.service.impl;

import com.simple.dao.domain.SkOrder;
import com.simple.service.SecKillService;
import com.simple.util.SecKillUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/12 6:18 下午
 * @Description：
 */
@Service
public class SecKillServiceImpl implements SecKillService {
    /** 自增ID，分布式环境使用分布式ID */
    private AtomicLong count= new AtomicLong();
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 秒杀实现
     * @param id
     * @param number
     * @return
     */
    @Override
    public int seckill(Long id, int number) {
        if (SecKillUtils.secKillFlag.get(id) != null){
            return 0;
        }
        //生成订单，orderId在线上需要使用分布式ID
        SkOrder skOrder = SkOrder.builder().orderId(count.getAndIncrement()).goodsId(id).userId(123L).build();
        Message<SkOrder> message = MessageBuilder.withPayload(skOrder).build();
        //tx-order，主题，随便取
        rocketMQTemplate.sendMessageInTransaction("tx-order", message, null);

        return 1;
    }
}
