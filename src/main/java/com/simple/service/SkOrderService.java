package com.simple.service;

import com.simple.dao.domain.SkOrder;

public interface SkOrderService {
    int deleteByPrimaryKey(Long id);

    int insert(SkOrder record);

    int insertSelective(SkOrder record);

    SkOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkOrder record);

    int updateByPrimaryKey(SkOrder record);
}