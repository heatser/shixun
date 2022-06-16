package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.StoreDao;
import com.example.domain.PageResult;
import com.example.domain.Product;
import com.example.domain.Store;
import com.example.service.StoreService;
import com.example.utils.AutoName;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class StoreServiceImpl extends ServiceImpl<StoreDao, Store> implements StoreService {

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private AutoName autoName;

    public int selectAmountById(int id){
        int amount = storeDao.selectAmountById(id);
        return amount;
    }

    public boolean save(Store store){

        String nowTime = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringUtils.randomAlphabetic(5);
        String randomInt = RandomStringUtils.randomNumeric(6);
        String name = nowTime.substring(10)+randomString + randomInt ;



        store.setNo(name);
        int i = storeDao.insert(store);
        if(i !=0){
            return true;
        }else {
            return false;
        }
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

    @Override
    public PageResult selectPage(PageResult pageResult) {

        Store store = pageResult.store;

        IPage page = new Page(pageResult.getPagenum(),pageResult.getPagesize());

        LambdaQueryWrapper<Store> lqw = new LambdaQueryWrapper();

        if(store != null){

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

        }
        IPage page1 = storeDao.selectPage(page, lqw);

        List<Store> rows = page.getRecords();

        long total = page1.getTotal();


        pageResult.setTotal(total);
        pageResult.setStorerows(rows);

        return  pageResult;
    }


}
