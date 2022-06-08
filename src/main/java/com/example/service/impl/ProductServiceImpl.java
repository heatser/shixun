package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.ProductDao;
import com.example.domain.OutOrder;
import com.example.domain.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {
    @Autowired
    private ProductDao productDao;

    public List<Product> selectAllAndDeleted(){
        List<Product> products = productDao.selectAllAndDeleted();

        return products;
    }

    public List<Product> selectOut(){

        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
        lqw.eq(Product::getType, "1");
        List<Product> products = productDao.selectList(lqw);
        return products;

    }

    public List<Product> selectIn(){
        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
        lqw.eq(Product::getType, "0");
        List<Product> products = productDao.selectList(lqw);
        return products;
    }

    public List<Product> selectByCondition(Product product){
        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();

        lqw.eq(Product::getType,product.getType());

        if(product.getNo()!=null)
        {
            lqw.like(Product::getNo,product.getNo());
        }
        if(product.getColor()!=null)
        {
            lqw.like(Product::getColor,product.getColor());
        }
        if(product.getName()!=null)
        {
            lqw.like(Product::getName,product.getName());
        }

        List<Product> products = productDao.selectList(lqw);

        return products;
    }


}
