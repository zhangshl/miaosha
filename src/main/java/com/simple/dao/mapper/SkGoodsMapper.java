package com.simple.dao.mapper;

import com.simple.dao.domain.SkGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SkGoodsMapper {
    int insert(SkGoods record);

    SkGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKey(@Param("id") Long id, @Param("number") int number);
}