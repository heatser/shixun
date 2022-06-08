package com.example.service.impl;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.UserDao;
import com.example.domain.Code;
import com.example.domain.OutOrder;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.utils.CodeUtils;
import com.example.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodeUtils codeUtils;

    @Autowired
    private SendMail sendMail;


    @CreateCache(name = "jetCache",expire = 120,cacheType = CacheType.BOTH)
    private Cache<String ,String> jetCache;


    public User login(User user) {
        User user1 = userDao.login(user);

        if(user1.getId()>0)
        {
            String userid = "userid";
            String id = String.valueOf(user1.getId());

            jetCache.put(userid,id);
        }

        return user1;

    }

    public String logininfo(){
        String userid = "userid";
        String id = jetCache.get(userid);
        return id;
    }


    public String loginByEmail(String email){
        String checkcode = codeUtils.generator(email);

        jetCache.put(email,checkcode);
        sendMail.sendMail(checkcode);
        return checkcode;
    }

    public boolean checkCode(String email,String checkcode) {
        String trueCode = jetCache.get(email);
        return checkcode.equals(trueCode);
    }


    public List<User> selectByName(String name) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.likeRight(User::getName, name);
        List<User> users = userDao.selectList(lqw);
        System.out.println(users);
        return users;

    }

    public User selectById(int id){
        User user = userDao.selectById(id);
        return user;
    }


    public List<User> selectByUserName(String name) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(User::getUsername, name);
        List<User> users = userDao.selectList(lqw);
        return users;

    }



    public List<User> selectByCondition(User user){

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        if(user.getName() != null)
        {
            lqw.like(User::getName, user.getName());
        }
        if(user.getUsername() != null)
        {
            lqw.like(User::getUsername, user.getUsername());
        }

        List<User> users = userDao.selectList(lqw);

        return users;
    }

}
