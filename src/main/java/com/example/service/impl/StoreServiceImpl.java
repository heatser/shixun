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
//
//    @Autowired
//    private AutoName autoName;

    public int selectAmountById(int id){  //根据id查询库存数量
        int amount = storeDao.selectAmountById(id);
        return amount;
    }

    public boolean save(Store store){  //新增库存商品

        //随机数构成商品货号
        String nowTime = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringUtils.randomAlphabetic(5);
        String randomInt = RandomStringUtils.randomNumeric(6);
        String name = nowTime.substring(10)+randomString + randomInt ;



        store.setNo(name);
        //判断是否为空
        int i = storeDao.insert(store);
        if(i !=0){
            return true;
        }else {
            return false;
        }
    }

    //根据明细来更改库存数量
    public boolean changeAmountByProduct(Product product){

        //根据明细来获取库存id
        Store store = storeDao.selectById(product.getStoreid());

        //判断为出库时，并且库存数量大于明细数量，做更新
        if(store.getAmount()>product.getAmount()&&product.getType().equals("1"))
        {
            store.setAmount(store.getAmount()-product.getAmount());
            storeDao.updateById(store);
        }
        //判断为入库时
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

//    public List<Store> selectByCondition(Store store){
//        LambdaQueryWrapper<Store> lqw = new LambdaQueryWrapper<Store>();
//
//        if(store.getName()!=null)
//        {
//            lqw.like(Store::getName,store.getName());
//        }
//        if(store.getNo()!=null)
//        {
//            lqw.like(Store::getNo,store.getNo());
//        }
//        if(store.getColor()!=null)
//        {
//            lqw.like(Store::getColor,store.getColor());
//        }
//
//        List<Store> list = storeDao.selectList(lqw);
//
//        return list;
//
//    }

    //根据库存商品id及数量来更改库存
    public boolean changeAmountByProductIdANDAmount(int id,int amount){
        Store store = storeDao.selectById(id);
        //如果库存数量大于传值数量，则做更新
        if(store.getAmount()>amount){
            store.setAmount(store.getAmount()-amount);
            storeDao.updateById(store);
        }else {
            return false;
        }
        return true;
    }

    @Override //分页并多条件查询
    public PageResult selectPage(PageResult pageResult) {

        Store store = pageResult.store;

        IPage page = new Page(pageResult.getPagenum(),pageResult.getPagesize());

        LambdaQueryWrapper<Store> lqw = new LambdaQueryWrapper();

        //做多条件判断
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

        //获取分页单页数据
        List<Store> rows = page.getRecords();

        //获取总页数
        long total = page1.getTotal();


        pageResult.setTotal(total);
        pageResult.setStorerows(rows);

        return  pageResult;
    }


}
