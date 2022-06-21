package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendMail {

    @Autowired
    private JavaMailSender javaMailSender;

    //发送人
    private String from = "a1361422853@163.com";
    //接收人
    private String to = "1361422853@qq.com";
    //标题
    private String subject = "验证码";
    //正文
    private String context = "你的验证码为：";


    //发送邮件及其信息
    public void sendMail(String checkcode) {

        String text = context + checkcode;

//        System.out.println(text);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from+"(hello)");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);

    }
}

















