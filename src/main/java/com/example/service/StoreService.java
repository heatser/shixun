package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Product;
import com.example.domain.Store;

import java.util.List;


public interface StoreService extends IService<Store> {

    public boolean changeAmountByProduct(Product product);

    public int selectAmountById(int id);

    public List<Store> selectByCondition(Store store);

    public boolean changeAmountByProductIdANDAmount(int id,int amount);





}
