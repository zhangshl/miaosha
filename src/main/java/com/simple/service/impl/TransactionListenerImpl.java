package com.simple.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.simple.dao.domain.SkOrder;
import com.simple.service.SkOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import com.simple.util.SecKillUtils;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/13 6:16 下午
 * @Description：
 */
@RocketMQTransactionListener()
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SkOrderService skOrderService;

    /**
     * 本地事务执行：经本地压测，单节点事务消息tps为2600左右，可通过分布式集群模式提高性能
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try{
            String orderMessage = new String((byte[]) message.getPayload());
            SkOrder skOrder = JSONObject.parseObject(orderMessage, SkOrder.class);
            long stock = redissonClient.getAtomicLong(String.valueOf(skOrder.getGoodsId())).decrementAndGet();
            if (stock<0){
                //秒杀结束标志
                SecKillUtils.secKillFlag.put(skOrder.getGoodsId(), false);
                redissonClient.getAtomicLong(String.valueOf(skOrder.getGoodsId())).incrementAndGet();
                return RocketMQLocalTransactionState.ROLLBACK;
            }
            //若有多个本地改库操作，需要使用事务
            skOrderService.insert(skOrder);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 回调接口
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        try{
            String orderMessage = new String((byte[]) message.getPayload());
            SkOrder skOrder = JSONObject.parseObject(orderMessage, SkOrder.class);
            SkOrder data = skOrderService.selectByPrimaryKey(skOrder.getOrderId());
            if (data == null){
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }

        return RocketMQLocalTransactionState.COMMIT;
    }
}
