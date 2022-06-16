package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dao.InOrderDao;
import com.example.dao.OutOrderDao;
import com.example.domain.*;
import com.example.service.OutOrderService;
import com.example.utils.AutoName;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OutOrderServiceImpl extends ServiceImpl<OutOrderDao, OutOrder> implements OutOrderService {

    @Autowired
    private OutOrderDao outOrderDao;

    @Autowired
    private AutoName autoName;


    public boolean save(OutOrder outOrder) {
        String nowTime = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringUtils.randomAlphabetic(5);
        String randomInt = RandomStringUtils.randomNumeric(6);
        String name = nowTime.substring(13)+randomString + randomInt ;
        outOrder.setNo(name);

        int i = outOrderDao.insert(outOrder);
        if (i != 0) {
            return true;
        } else {
            return false;
        }

    }


    public List<OutOrder> selectAllAndDeleted() {
        List<OutOrder> outOrders = outOrderDao.selectAllAndDeleted();
        return outOrders;
    }

    public List<OutOrder> selectByStore(String store) {
        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper<OutOrder>();
        lqw.like(OutOrder::getStore, store);
        List<OutOrder> outOrders = outOrderDao.selectList(lqw);
        return outOrders;
    }

    public List<OutOrder> selectByDate(Date date) {
        List<OutOrder> outOrders = outOrderDao.selectAllAndDeleted();
        return outOrders;
    }

    public List<OutOrder> selectByNo(String no) {
        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper<OutOrder>();
        lqw.like(OutOrder::getNo, no);
        List<OutOrder> outOrders = outOrderDao.selectList(lqw);
        return outOrders;
    }


    public List<OutOrder> selectByCondition(OutOrder outOrder) {

        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper<OutOrder>();
        if (outOrder.getNo() != null) {
            lqw.like(OutOrder::getNo, outOrder.getNo());
        }
        if (outOrder.getStore() != null) {
            lqw.like(OutOrder::getStore, outOrder.getStore());
        }

        List<OutOrder> outOrders = outOrderDao.selectList(lqw);

        return outOrders;
    }

    @Override
    public PageResult selectPage(PageResult pageResult) {

        OutOrder outOrder = pageResult.outOrder;

        IPage page = new Page(pageResult.getPagenum(), pageResult.getPagesize());

        LambdaQueryWrapper<OutOrder> lqw = new LambdaQueryWrapper();

        if (outOrder != null) {

            if (outOrder.getNo() != null) {
                lqw.like(OutOrder::getNo, outOrder.getNo());
            }
            if (outOrder.getStore() != null) {
                lqw.like(OutOrder::getStore, outOrder.getStore());
            }

        }
        IPage page1 = outOrderDao.selectPage(page, lqw);

        List<OutOrder> rows = page.getRecords();

        long total = page1.getTotal();

        pageResult.setTotal(total);
        pageResult.setOutOrderrows(rows);

        return pageResult;
    }


}
