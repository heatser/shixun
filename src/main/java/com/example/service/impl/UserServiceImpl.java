package com.example.service.impl;


import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.UserDao;
import com.example.domain.PageResult;
import com.example.domain.Store;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.utils.CodeUtils;
import com.example.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodeUtils codeUtils;

    @Autowired
    private SendMail sendMail;


    @CreateCache(name = "jetCache", expire = 120, cacheType = CacheType.BOTH)
    private Cache<String, String> jetCache;

    @CreateCache(name = "userCache", expire = 600, cacheType = CacheType.BOTH)
    private Cache<String, String> userCache;


    public User login(User user) {

        User user1 = userDao.login(user);

        if (user1 != null) {
            String id = String.valueOf(user1.getId());
            String userid = "userid";
            userCache.put(userid, id);
        }
        return user1;

    }

    public String logininfo() {
        String userid = "userid";
        String id = userCache.get(userid);

        System.out.println("--------------");
        System.out.println(id);
        System.out.println("----------------");



        return id;
    }


    public String loginByEmail(String email) {
        String checkcode = codeUtils.generator(email);

        jetCache.put(email, checkcode);
        sendMail.sendMail(checkcode);
        return checkcode;
    }

    public boolean checkCode(String email, String checkcode) {
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

    public User selectById(int id) {
        User user = userDao.selectById(id);
        return user;
    }


    public List<User> selectByUserName(String name) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(User::getUsername, name);
        List<User> users = userDao.selectList(lqw);
        return users;

    }


    public List<User> selectByCondition(User user) {

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        if (user.getName() != null) {
            lqw.like(User::getName, user.getName());
        }
        if (user.getUsername() != null) {
            lqw.like(User::getUsername, user.getUsername());
        }

        List<User> users = userDao.selectList(lqw);

        return users;
    }


    public PageResult selectPage(PageResult pageResult) {

        User user = pageResult.user;

        IPage page = new Page(pageResult.getPagenum(),pageResult.getPagesize());

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper();

        if(user != null){

            if (user.getName() != null) {
                lqw.like(User::getName, user.getName());
            }
            if (user.getUsername() != null) {
                lqw.like(User::getUsername, user.getUsername());
            }

        }

        IPage page1 = userDao.selectPage(page, lqw);

        List<User> rows = page.getRecords();

        long total = page1.getTotal();


        pageResult.setTotal(total);
        pageResult.setUserrows((rows));

        return  pageResult;
    }

}
