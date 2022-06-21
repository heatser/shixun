package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InOrderDao extends BaseMapper<InOrder> {

    @Select("select * from inorder ")
    //查询所有包括逻辑删除
    public List<InOrder> selectAllAndDeleted();

}
