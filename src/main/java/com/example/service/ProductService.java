package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.OutOrder;
import com.example.domain.Product;
import com.example.domain.User;

import java.util.List;

public interface ProductService extends IService<Product> {

    public List<Product> selectAllAndDeleted();





}
