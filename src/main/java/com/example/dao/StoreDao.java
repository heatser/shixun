package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StoreDao extends BaseMapper<Store> {



    @Select("select amount from store where id=#{id}")
    //根据id来查询数量
    public int selectAmountById(int id);



}
