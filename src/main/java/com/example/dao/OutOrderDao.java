package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.OutOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OutOrderDao extends BaseMapper<OutOrder> {

    @Select("select * from outorder ")
    //查询所有包括逻辑删除
    public List<OutOrder> selectAllAndDeleted();

}
