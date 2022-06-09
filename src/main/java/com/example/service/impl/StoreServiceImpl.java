package com.example.service.impl;

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


        if(store.getAmount()>product.getAmount()&&product.getType()=="1")
        {
            store.setAmount(store.getAmount()-product.getAmount());
            storeDao.updateById(store);
        }
        else if (product.getType()=="0")
        {
            store.setAmount(store.getAmount()+product.getAmount());
            storeDao.updateById(store);
        }
        else{
            return false;
        }
        return true;
    }



}
