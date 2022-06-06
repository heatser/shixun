package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.User;

import java.util.Date;
import java.util.List;

public interface InOrderService extends IService<InOrder> {

    public List<InOrder> selectAll();

    public List<InOrder> selectByStore(String name);

    public List<InOrder> selectByDate(Date date);

    public List<InOrder> selectByNo(String no);


}
