package com.example.controller;


import com.example.domain.Code;
import com.example.domain.Product;
import com.example.domain.Result;
import com.example.service.ProductService;
import com.example.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;


    @GetMapping
    public Result selectAll() {
        List<Product> list = productService.list(null);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @GetMapping("{id}")
    public Result selectById(@PathVariable int id) {
        Product product = productService.getById(id);
        int code = product != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = product != null ? "" : "ERROR";
        return new Result(code, product, msg);
    }


    @GetMapping("/out")
    public Result selectOut() {
        List<Product> list = productService.selectOut();
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @GetMapping("/in")
    public Result selectIn() {
        List<Product> list = productService.selectIn();
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @PostMapping("/condition")
    public Result selectByCondition(@RequestBody Product product) {
        List<Product> list = productService.selectByCondition(product);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @PostMapping
    public Result save(@RequestBody Product product) {
        boolean flag = false;

        //库存管理

        int code = 0;
        String msg = "";

        boolean flag1 = storeService.changeAmountByProduct(product);
        if (flag1 == true) {

            flag = productService.save(product);
            code = flag != false ? Code.SAVE_OK : Code.SAVE_ERR;
            msg = flag != false ? "" : "ERROR";
        } else {
            code = Code.SAVE_ERR;
            msg = "ERROR";
        }
        return new Result(code, flag, msg);
    }

    @PutMapping
    public Result update(@RequestBody Product product) {
        boolean flag = false;


        //库存管理
        Product product1 = productService.getById(product.getId());
        int amount1 = product1.getAmount();

        int amount = product.getAmount();

        amount1 = amount - amount1;

        if(product.getType().equals("0")){
            amount1 = -amount1;
        }


        boolean flag1 = storeService.changeAmountByProductIdANDAmount(product.getId(), amount1);
        if(flag1=true)
        {
            flag = productService.updateById(product);
        }


        int code = flag != false ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @DeleteMapping("{id}")

    public Result delete(@PathVariable int id) {
        boolean flag = productService.removeById(id);
        int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }
}
