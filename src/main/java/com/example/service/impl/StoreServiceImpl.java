package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.StoreDao;
import com.example.domain.Product;
import com.example.domain.Store;
import com.example.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StoreServiceImpl extends ServiceImpl<StoreDao, Store> implements StoreService {

    @Autowired
    private StoreDao storeDao;

    public int selectAmountById(int id){
        int amount = storeDao.selectAmountById(id);
        return amount;
    }

    public boolean changeAmountByProduct(Product product){
        Store store = storeDao.selectById(product.getStoreid());


        if(store.getAmount()>product.getAmount()&&product.getType().equals("1"))
        {
            store.setAmount(store.getAmount()-product.getAmount());
            storeDao.updateById(store);
        }
        else if (product.getType().equals("0"))
        {
            store.setAmount(store.getAmount()+product.getAmount());
            storeDao.updateById(store);
        }
        else{
            return false;
        }
        return true;
    }

    public List<Store> selectByCondition(Store store){
        LambdaQueryWrapper<Store> lqw = new LambdaQueryWrapper<Store>();

        if(store.getName()!=null)
        {
            lqw.like(Store::getName,store.getName());
        }
        if(store.getNo()!=null)
        {
            lqw.like(Store::getNo,store.getNo());
        }
        if(store.getColor()!=null)
        {
            lqw.like(Store::getColor,store.getColor());
        }
        List<Store> list = storeDao.selectList(lqw);
        return list;

    }


    public boolean changeAmountByProductIdANDAmount(int id,int amount){
        Store store = storeDao.selectById(id);
        if(store.getAmount()>amount){
            store.setAmount(store.getAmount()-amount);
            storeDao.updateById(store);
        }else {
            return false;
        }
        return true;
    }



}
