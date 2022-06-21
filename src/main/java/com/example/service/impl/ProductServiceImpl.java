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

    public List<Product> selectAllAndDeleted(){    //查询所有入库单数据（包括逻辑删除的），但没有使用
        List<Product> products = productDao.selectAllAndDeleted();

        return products;
    }

//    public List<Product> selectOut(){
//
//        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//        lqw.eq(Product::getType, "1");
//        List<Product> products = productDao.selectList(lqw);
//        return products;
//
//    }
//
//    public List<Product> selectIn(){
//        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//        lqw.eq(Product::getType, "0");
//        List<Product> products = productDao.selectList(lqw);
//        return products;
//    }
//
//    public List<Product> selectByCondition(Product product){
//        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
//
//        lqw.eq(Product::getType,product.getType());
//
//
//        int orderid = product.getOrderid();
//        if(orderid!=0)
//        {
//            lqw.eq(Product::getOrderid,product.getOrderid());
//        }
//
//        if(product.getNo()!=null)
//        {
//            lqw.like(Product::getNo,product.getNo());
//        }
//        if(product.getColor()!=null)
//        {
//            lqw.like(Product::getColor,product.getColor());
//        }
//        if(product.getName()!=null)
//        {
//            lqw.like(Product::getName,product.getName());
//        }
//
//        List<Product> products = productDao.selectList(lqw);
//
//        return products;
//    }


    @Override   //分页同时多条件查询
    public PageResult selectPage(PageResult pageResult) {

        Product product = pageResult.product;

        IPage page = new Page(pageResult.getPagenum(),pageResult.getPagesize());

        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper();

        //做多条件判断
        if(product != null){

            //做出库入库判断
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

        //获取分页单页数据
        List<Product> rows = page.getRecords();

        //获取总页数
        long total = page1.getTotal();

        pageResult.setTotal(total);
        pageResult.setProductrows(rows);

        return  pageResult;
    }

    @Override   //新增明细
    public boolean save(Product product) {

        Store store = storeDao.selectById(product.getStoreid());
        //判断是否为空
        if(product.getOrderid()!=0){
            product.setName(store.getName());
            product.setColor(store.getColor());
            product.setSize(store.getSize());
        }

        //随机数组成明细编号
        String randomString = RandomStringUtils.randomAlphabetic(5);
        String randomInt = RandomStringUtils.randomNumeric(6);

        String in = "in";
        String out = "out";


        //做随机明细货号处理
        if(product.getType().equals("0")){
            String no = in + randomString  + randomInt;
            product.setNo(no);
        }else {
            String no = out + randomString  + randomInt;
            product.setNo(no);
        }

        productDao.insert(product);
        return true;
    }

//    @Override
//    public boolean changeno(int id) {
//        Product product = productDao.selectById(id);
//        Store store = storeDao.selectById(product.getStoreid());
//
//        String in = "in";
//        String out = "out";
//        String storeno = store.getNo();
//        String nowTime = String.valueOf(System.currentTimeMillis());
//
//
//        if(product.getType().equals("0")){
//            String no = in + storeno + nowTime;
//            product.setNo(no);
//        }else {
//            String no = out + storeno + nowTime;
//            product.setNo(no);
//        }
//
//        productDao.updateById(product);
//
//        return true;
//    }

}
