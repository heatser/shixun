package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.InOrderDao;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.PageResult;
import com.example.service.InOrderService;
import com.example.utils.AutoName;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class InOrderServiceImpl extends ServiceImpl<InOrderDao, InOrder> implements InOrderService {

    @Autowired
    private InOrderDao inOrderDao;

//    @Autowired
//    private AutoName autoName;

    public List<InOrder> selectAllAndDeleted(){  //查询所有入库单数据（包括逻辑删除的），但没有使用
        List<InOrder> inOrders = inOrderDao.selectAllAndDeleted();
        return inOrders;
    }

//    public List<InOrder> selectByStore(String store){
//        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper<InOrder>();
//        lqw.like(InOrder::getStore, store);
//        List<InOrder> inOrders = inOrderDao.selectList(lqw);
//        return inOrders;
//    }

//    public List<InOrder> selectByDate(Date date){
//        List<InOrder> inOrders = inOrderDao.selectAllAndDeleted();
//        return inOrders;
//    }

//    public List<InOrder> selectByNo(String no){
//        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper<InOrder>();
//        lqw.like(InOrder::getNo, no);
//        List<InOrder> inOrders = inOrderDao.selectList(lqw);
//        return inOrders;
//    }

    public InOrder selectById(int id){  //根据id查询单行数据
        InOrder inOrder = inOrderDao.selectById(id);
        return inOrder;
    }

//    public List<InOrder> selectByCondition(InOrder inOrder){
//
//        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper<InOrder>();
//        if(inOrder.getNo() != null)
//        {
//            lqw.like(InOrder::getNo, inOrder.getNo());
//        }
//        if(inOrder.getStore() != null)
//        {
//            lqw.like(InOrder::getStore, inOrder.getStore());
//        }
//
//        List<InOrder> inOrders = inOrderDao.selectList(lqw);
//
//        return inOrders;
//    }

    @Override  //分页同时多条件查询
    public PageResult selectPage(PageResult pageResult) {

        InOrder inOrder = pageResult.inOrder;

        IPage page = new Page(pageResult.getPagenum(),pageResult.getPagesize());

        LambdaQueryWrapper<InOrder> lqw = new LambdaQueryWrapper();

        //做多条件判断
        if(inOrder != null){

            if(inOrder.getNo() != null)
            {
                lqw.like(InOrder::getNo, inOrder.getNo());
            }
            if(inOrder.getStore() != null)
            {
                lqw.like(InOrder::getStore, inOrder.getStore());
            }

        }
        IPage page1 = inOrderDao.selectPage(page, lqw);

        //获取分页单页数据
        List<InOrder> rows = page.getRecords();

        //获取总页数
        long total = page1.getTotal();

        pageResult.setTotal(total);
        pageResult.setInOrderrows(rows);
        return  pageResult;
    }

    @Override  //新增入库单
    public boolean save(InOrder inOrder) {

        //随机数加当前时间组成单据编号
        String nowTime = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringUtils.randomAlphabetic(5);
        String randomInt = RandomStringUtils.randomNumeric(6);
        String name = nowTime.substring(13)+randomString + randomInt ;


        inOrder.setNo(name);
        int insert = inOrderDao.insert(inOrder);
        if(insert != 0){
            return true;
        }else {
            return false;
        }
    }

}
