package com.example.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CodeUtils {

    private String [] patch = {"000000","00000","0000","000","00","0",""};

    //随机生成验证码
    public String generator(String tele){
        //获取哈希值
        int hash = tele.hashCode();
        int encryption = 20206666;
        long result = hash ^ encryption;
        //获取当前时间
        long nowTime = System.currentTimeMillis();
        result = result ^ nowTime;
        long code = result % 1000000;
        code = code < 0 ? -code : code;
        String codeStr = code + "";
        int len = codeStr.length();
        return patch[len] + codeStr;
    }

    @Cacheable(value = "Code",key="#tele")
    public String get(String tele){
        return null;
    }

}