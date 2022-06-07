package com.example.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.example.domain.Code;
import com.example.domain.OutOrder;
import com.example.domain.Result;
import com.example.service.OutOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/outorder")
public class OutOrderController {

    @Autowired
    private OutOrderService outOrderService;

    @GetMapping
    public Result selectAll(){
        List<OutOrder> list = outOrderService.list(null);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/store/{store}")
    public Result selectByStore(@PathVariable String store){
        List<OutOrder> list = outOrderService.selectByStore(store);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/date/{date}")
    public Result selectByDate(@PathVariable Date date){
        List<OutOrder> list = outOrderService.selectByDate(date);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/no/{no}")
    public Result selectByNo(@PathVariable String no){
        List<OutOrder> list = outOrderService.selectByNo(no);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @PostMapping
    public Result save(@RequestBody OutOrder outOrder){
        java.util.Date day=new Date();
        outOrder.setDate(day);
        boolean flag = outOrderService.save(outOrder);
        int code = flag != false ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @PutMapping("{id}")
    public Result delete(@PathVariable int id){
        boolean flag = outOrderService.removeById(id);
        int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }

    @PutMapping
    public Result update(@RequestBody OutOrder outOrder)
    {
        boolean flag = outOrderService.updateById(outOrder);
        int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }
}
