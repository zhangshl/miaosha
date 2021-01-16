package com.simple.service.impl;

import com.simple.dao.domain.SkOrder;
import com.simple.dao.mapper.SkOrderMapper;
import com.simple.service.SkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/13 8:59 下午
 * @Description：
 */
@Service
public class SkOrderServiceImpl implements SkOrderService {

    @Autowired
    private SkOrderMapper skOrderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(SkOrder record) {
        return skOrderMapper.insert(record);
    }

    @Override
    public int insertSelective(SkOrder record) {
        return skOrderMapper.insertSelective(record);
    }

    @Override
    public SkOrder selectByPrimaryKey(Long id) {
        return skOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SkOrder record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(SkOrder record) {
        return 0;
    }
}
