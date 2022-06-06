package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.InOrder;
import com.example.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductDao extends BaseMapper<Product> {

    @Select("select * from product ")
    public List<Product> selectAll();
}
