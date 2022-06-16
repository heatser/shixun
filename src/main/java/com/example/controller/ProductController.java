package com.example.controller;


import com.example.domain.*;
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


        boolean flag1 = storeService.changeAmountByProductIdANDAmount(product.getStoreid(), amount1);
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
        Product product = productService.getById(id);
        Store store = storeService.getById(product.getStoreid());

        int amount1 = store.getAmount();
        int amount = product.getAmount();
        amount1 = amount1 - amount;


        if(product.getType().equals("0") && amount1>0){
            boolean flag = productService.removeById(id);
            boolean flag1 = storeService.changeAmountByProductIdANDAmount(store.getId(), amount);
            if(flag1 == flag){
                int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
                String msg = flag != false ? "" : "ERROR";
                return new Result(code, flag, msg);
            }
            else{
                return new Result(Code.DELETE_ERR,false,"error");
            }
        }else if(product.getType().equals("1")){
            store.setAmount(store.getAmount()+amount);
            storeService.updateById(store);
            boolean flag = productService.removeById(id);
            int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
            String msg = flag != false ? "" : "ERROR";
            return new Result(code, flag, msg);
        }else {
            return new Result(Code.DELETE_ERR,false,"error");
        }

    }



    @PostMapping("/in/page")
    public Result selectInPage(@RequestBody PageResult pageResult) {

        PageResult page = productService.selectPage(pageResult);

        int code = page != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = page != null ? "" : "ERROR";
        return new Result(code, page, msg);
    }


    @PostMapping("/out/page")
    public Result selectOutPage(@RequestBody PageResult pageResult) {

        PageResult page = productService.selectPage(pageResult);

        int code = page != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = page != null ? "" : "ERROR";
        return new Result(code, page, msg);
    }





    @GetMapping("/no/{id}")
    public Result changeNo(@PathVariable int id){
        boolean flag = productService.changeno(id);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }
}
