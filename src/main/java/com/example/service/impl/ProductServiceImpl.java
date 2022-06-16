package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.ProductDao;
import com.example.dao.StoreDao;
import com.example.domain.PageResult;
import com.example.domain.Product;
import com.example.domain.Store;
import com.example.service.ProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private StoreDao storeDao;

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


        int orderid = product.getOrderid();
        if(orderid!=0)
        {
            lqw.eq(Product::getOrderid,product.getOrderid());
        }

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


    @Override
    public PageResult selectPage(PageResult pageResult) {

        Product product = pageResult.product;

        IPage page = new Page(pageResult.getPagenum(),pageResult.getPagesize());

        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper();

        if(product != null){

            int orderid = product.getOrderid();

            lqw.eq(Product::getType,product.getType());

            if(orderid!=0)
            {
                lqw.eq(Product::getOrderid,product.getOrderid());
            }

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

        }

        IPage page1 = productDao.selectPage(page, lqw);

        List<Product> rows = page.getRecords();

        long total = page1.getTotal();

        pageResult.setTotal(total);
        pageResult.setProductrows(rows);

        return  pageResult;
    }

    @Override
    public boolean save(Product product) {

        Store store = storeDao.selectById(product.getStoreid());
        if(product.getOrderid()!=0){
            product.setName(store.getName());
            product.setColor(store.getColor());
            product.setSize(store.getSize());
        }

//        String nowTime = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringUtils.randomAlphabetic(5);
        String randomInt = RandomStringUtils.randomNumeric(6);

        String in = "in";
        String out = "out";


        if(product.getType().equals("0")){
            String no = in + randomString  + randomInt;
//            String no = in ;
            product.setNo(no);
        }else {
            String no = out + randomString  + randomInt;
//            String no = out;
            product.setNo(no);
        }

        productDao.insert(product);
        return true;
    }

    @Override
    public boolean changeno(int id) {
        Product product = productDao.selectById(id);
        Store store = storeDao.selectById(product.getStoreid());

        String in = "in";
        String out = "out";
        String storeno = store.getNo();
        String nowTime = String.valueOf(System.currentTimeMillis());


        if(product.getType().equals(0)){
            String no = in + storeno + nowTime;
            product.setNo(no);
        }else {
            String no = out + storeno + nowTime;
            product.setNo(no);
        }

        productDao.updateById(product);

        return true;
    }

}
