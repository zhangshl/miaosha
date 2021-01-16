package com.simple.service.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.simple.dao.domain.SkGoods;
import com.simple.dao.domain.SkOrder;
import com.simple.dao.mapper.SkGoodsMapper;
import com.simple.service.SecKillService;
import com.simple.service.util.SecKillUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/12 6:18 下午
 * @Description：
 */
@Service
public class SecKillServiceImpl implements SecKillService, InitializingBean {
    @Autowired
    private SkGoodsMapper skGoodsMapper;

    private AtomicLong count= new AtomicLong();
    /**秒杀商品本地缓存*/
    private LoadingCache<Long, SkGoods> localCache;
    private static final int TIME_OUT = 24;

    @Autowired
    private RedissonClient redissonClient;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public int insert(SkGoods record) {
        return skGoodsMapper.insert(record);
    }

    /**
     * 查询商品
     * @param id
     * @return
     */
    @Override
    public SkGoods selectByPrimaryKey(Long id) {
        if (localCache.get(id)!=null){
            return localCache.get(id);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count.addAndGet(1));
        SkGoods skGoods = skGoodsMapper.selectByPrimaryKey(id);

        //放入缓存
        localCache.put(id, skGoods);
        redissonClient.getAtomicLong(String.valueOf(id)).set(skGoods.getGoodsStock());
        redissonClient.getAtomicLong(String.valueOf(id)).expire(3, TimeUnit.DAYS);
        System.out.println(redissonClient.getAtomicLong(String.valueOf(id)).get());
        return skGoods;
    }

    /**
     *
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


    /**
     * 初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        localCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(TIME_OUT, TimeUnit.HOURS)
                .build(k -> getValue(k));

        Long id = 3L;
        SkGoods skGoods = skGoodsMapper.selectByPrimaryKey(id);
        localCache.put(id, skGoods);
        redissonClient.getAtomicLong(String.valueOf(id)).set(skGoods.getGoodsStock());
    }

    public SkGoods getValue(Long key){
        return null;
    }
}
