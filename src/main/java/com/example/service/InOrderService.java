package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.PageResult;
import com.example.domain.User;

import java.util.Date;
import java.util.List;

public interface InOrderService extends IService<InOrder> {

    public List<InOrder> selectAllAndDeleted();

    public List<InOrder> selectByStore(String name);

    public List<InOrder> selectByDate(Date date);

    public List<InOrder> selectByNo(String no);

    public InOrder selectById(int id);

    public List<InOrder> selectByCondition(InOrder inOrder);

    public PageResult selectPage(PageResult pageResult);

    public boolean save(InOrder inOrder);

}
