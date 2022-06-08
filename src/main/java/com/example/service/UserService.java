package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.OutOrder;
import com.example.domain.User;

import java.util.List;

public interface UserService extends IService<User> {

    public User login(User user);


    public List<User> selectByName(String name);

    public List<User> selectByUserName(String name);

    public User selectById(int id);

    public String loginByEmail(String email);

    public boolean checkCode(String email,String checkcode);

    public List<User> selectByCondition(User user);

}
