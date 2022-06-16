package com.example.utils;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class AutoName {

    String nowTime = String.valueOf(System.currentTimeMillis());
    String randomString = RandomStringUtils.randomAlphabetic(5);
    String randomInt = RandomStringUtils.randomNumeric(6);


    public String AutoOrderName(){
        String name = nowTime.substring(14)+randomString + randomInt;
        return name;
    }

    public String AutoStoreName(){
        String name = nowTime.substring(10)+randomString + randomInt ;
        return name;
    }


}
