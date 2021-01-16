package com.simple.dao.mapper;

import com.simple.dao.domain.SkOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SkOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SkOrder record);

    int insertSelective(SkOrder record);

    SkOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SkOrder record);

    int updateByPrimaryKey(SkOrder record);
}