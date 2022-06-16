package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.PageResult;
import com.example.domain.User;

import java.util.Date;
import java.util.List;

public interface OutOrderService extends IService<OutOrder> {

    public List<OutOrder> selectAllAndDeleted();

    public List<OutOrder> selectByStore(String name);

    public List<OutOrder> selectByDate(Date date);

    public List<OutOrder> selectByNo(String no);

    public List<OutOrder> selectByCondition(OutOrder outOrder);

    public PageResult selectPage(PageResult pageResult);

    public boolean save(OutOrder outOrder);
}
