package com.simple.service;

import com.simple.dao.domain.SkGoods;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/12 5:37 下午
 * @Description：
 */
public interface SecKillService {

    int insert(SkGoods record);

    SkGoods selectByPrimaryKey(Long id);

    int seckill(Long id, int number);
}
