package com.example.controller;


import com.example.domain.Code;
import com.example.domain.Product;
import com.example.domain.Result;
import com.example.domain.Store;
import com.example.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PutMapping("/product")
    public Result changeAmountByProduct(@RequestBody Product product){
        boolean flag = storeService.changeAmountByProduct(product);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_OK;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @GetMapping
    public Result selectAll(){
        List<Store> list = storeService.list();
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @PutMapping
    public Result update(@RequestBody Store store){
        boolean flag = storeService.updateById(store);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_OK;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @PostMapping
    public Result sava(@RequestBody Store store){
        boolean flag = storeService.save(store);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_OK;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

}
