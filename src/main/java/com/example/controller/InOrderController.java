package com.example.controller;


import com.example.domain.Code;
import com.example.domain.InOrder;
import com.example.domain.OutOrder;
import com.example.domain.Result;
import com.example.service.InOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inorder")
public class InOrderController {

    @Autowired
    private InOrderService inOrderService;

    @GetMapping
    public Result selectAll(){
        List<InOrder> list = inOrderService.selectAll();
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/store/{store}")
    public Result selectByStore(@PathVariable String store){
        List<InOrder> list = inOrderService.selectByStore(store);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/date/{date}")
    public Result selectByDate(@PathVariable Date date){
        List<InOrder> list = inOrderService.selectByDate(date);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/no/{no}")
    public Result selectByNo(@PathVariable String no){
        List<InOrder> list = inOrderService.selectByNo(no);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @PostMapping
    public Result save(@RequestBody InOrder inOrder){
        java.util.Date day=new Date();
        inOrder.setDate(day);
        boolean flag = inOrderService.save(inOrder);
        int code = flag != false ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @PutMapping("{id}")
    public Result delete(@PathVariable int id){
        boolean flag = inOrderService.removeById(id);
        int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @PutMapping
    public Result update(@RequestBody InOrder inOrder)
    {
        boolean flag = inOrderService.updateById(inOrder);
        int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }
}
