package com.simple.service.impl;

import com.simple.dao.domain.SkOrder;
import com.simple.enums.ResultEnum;
import com.simple.service.SecKillService;
import com.simple.service.SkOrderService;
import com.simple.util.SecKillUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SkOrderService skOrderService;


    /**
     * 秒杀实现
     * @param id
     * @param number
     * @return
     */
    @Override
    public int seckill(Long id, int number) {
        if (SecKillUtils.secKillFlag.get(id) != null){
            return ResultEnum.FAIL.getCode();
        }
        //生成订单，orderId在线上需要使用分布式ID
        SkOrder skOrder = SkOrder.builder().orderId(count.getAndIncrement()).goodsId(id).userId(123L).build();
        long stock = redissonClient.getAtomicLong(String.valueOf(skOrder.getGoodsId())).decrementAndGet();
        if (stock<0){
            //秒杀结束标志
            SecKillUtils.secKillFlag.put(skOrder.getGoodsId(), false);
            redissonClient.getAtomicLong(String.valueOf(skOrder.getGoodsId())).incrementAndGet();
            return ResultEnum.FAIL.getCode();
        }
        //若有多个本地改库操作，需要使用事务
        try {
            int result = skOrderService.insert(skOrder);
        }catch (Exception e){
            //插入订单失败，回滚库存
            redissonClient.getAtomicLong(String.valueOf(skOrder.getGoodsId())).incrementAndGet();
            return ResultEnum.FAIL.getCode();
        }

        return ResultEnum.SUCCESS.getCode();
    }
}
