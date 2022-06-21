package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from user where username=#{username} AND password=#{password} AND deleted = 0  ")
    //登录判断
    public User login(User user);



}
