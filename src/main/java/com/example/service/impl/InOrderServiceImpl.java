package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.InOrderDao;
import com.example.domain.InOrder;
import com.example.service.InOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InOrderServiceImpl extends ServiceImpl<InOrderDao, InOrder> implements InOrderService {

    @Autowired
    private InOrderDao inOrderDao;

    public List<InOrder> selectAllAndDeleted(){
        List<InOrder> inOrders = inOrderDao.selectAllAndDeleted();
        return inOrders;
    }

    public List<InOrder> selectByStore(String store){
        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper<InOrder>();
        lqw.like(InOrder::getStore, store);
        List<InOrder> inOrders = inOrderDao.selectList(lqw);
        return inOrders;
    }

    public List<InOrder> selectByDate(Date date){
        List<InOrder> inOrders = inOrderDao.selectAllAndDeleted();
        return inOrders;
    }

    public List<InOrder> selectByNo(String no){
        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper<InOrder>();
        lqw.like(InOrder::getNo, no);
        List<InOrder> inOrders = inOrderDao.selectList(lqw);
        return inOrders;
    }

    public InOrder selectById(int id){
        InOrder inOrder = inOrderDao.selectById(id);
        return inOrder;
    }

    public List<InOrder> selectByCondition(InOrder inOrder){

        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper<InOrder>();
        if(inOrder.getNo() != null)
        {
            lqw.like(InOrder::getNo, inOrder.getNo());
        }
        if(inOrder.getStore() != null)
        {
            lqw.like(InOrder::getStore, inOrder.getStore());
        }

        List<InOrder> inOrders = inOrderDao.selectList(lqw);

        return inOrders;
    }

}
