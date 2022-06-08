package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dao.InOrderDao;
import com.example.dao.OutOrderDao;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.User;
import com.example.service.OutOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OutOrderServiceImpl extends ServiceImpl<OutOrderDao, OutOrder> implements OutOrderService {

    @Autowired
    private OutOrderDao outOrderDao;



    public List<OutOrder> selectAllAndDeleted(){
        List<OutOrder> outOrders = outOrderDao.selectAllAndDeleted();
        return outOrders;
    }

    public List<OutOrder> selectByStore(String store){
        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper<OutOrder>();
        lqw.like(OutOrder::getStore, store);
        List<OutOrder> outOrders = outOrderDao.selectList(lqw);
        return outOrders;
    }

    public List<OutOrder> selectByDate(Date date){
        List<OutOrder> outOrders = outOrderDao.selectAllAndDeleted();
        return outOrders;
    }

    public List<OutOrder> selectByNo(String no){
        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper<OutOrder>();
        lqw.like(OutOrder::getNo, no);
        List<OutOrder> outOrders = outOrderDao.selectList(lqw);
        return outOrders;
    }


    public List<OutOrder> selectByCondition(OutOrder outOrder){

        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper<OutOrder>();
        if(outOrder.getNo() != null)
        {
            lqw.like(OutOrder::getNo, outOrder.getNo());
        }
        if(outOrder.getStore() != null)
        {
            lqw.like(OutOrder::getStore, outOrder.getStore());
        }

        List<OutOrder> outOrders = outOrderDao.selectList(lqw);

        return outOrders;
    }



}
