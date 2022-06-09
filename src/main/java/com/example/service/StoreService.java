package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Product;
import com.example.domain.Store;


public interface StoreService extends IService<Store> {

    public boolean changeAmountByProduct(Product product);

    public int selectAmountById(int id);





}
