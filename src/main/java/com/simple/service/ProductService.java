package com.simple.service;

import com.simple.dao.domain.SkGoods;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/12 5:37 下午
 * @Description：商品服务
 */
public interface ProductService {

    int insert(SkGoods record);

    SkGoods selectByPrimaryKey(Long id);

    SkGoods reset(Long id);

}
