package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.*;
import com.example.service.StoreService;
import com.example.utils.AutoName;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;
//    @Autowired
//    private AutoName autoName;
//
//    @PutMapping("/changename/{id}")
//    public Result changeName(@PathVariable int id){
//        String nowTime = String.valueOf(System.currentTimeMillis());
//        String randomString = RandomStringUtils.randomAlphabetic(5);
//        String randomInt = RandomStringUtils.randomNumeric(6);
//        String name = nowTime.substring(10)+randomString + randomInt ;
//
//        Store store = storeService.getById(id);
//        store.setNo(name);
//        storeService.updateById(store);
//        return new Result(Code.UPDATE_OK,true,"");
//    }



    @PutMapping("/product")
    public Result changeAmountByProduct(@RequestBody Product product) {
        boolean flag = storeService.changeAmountByProduct(product);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_OK;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @GetMapping
    public Result selectAll() {
        List<Store> list = storeService.list();
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @PostMapping("/condition")
    public Result selectByCondition(@RequestBody Store store) {
        List<Store> list = storeService.selectByCondition(store);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @PutMapping
    public Result update(@RequestBody Store store) {
        if(store.getAmount()<0){
            int code = Code.UPDATE_ERR;
            return new Result(code,false,"error");
        }
        boolean flag = storeService.updateById(store);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_OK;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @PostMapping
    public Result sava(@RequestBody Store store) {
        boolean flag = storeService.save(store);
        int code = flag != false ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }


    @GetMapping("{id}")
    public Result selectById(@PathVariable int id){
        Store store = storeService.getById(id);
        int code = store != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = store != null ? "" : "ERROR";
        return new Result(code, store, msg);
    }


    @PostMapping("/page")
    public Result selectPage(@RequestBody PageResult pageResult) {

        PageResult page = storeService.selectPage(pageResult);

        int code = page != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = page != null ? "" : "ERROR";
        return new Result(code, page, msg);
    }

}
