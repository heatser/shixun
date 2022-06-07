package com.example.domain;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("store")
public class Store {
    @TableId(type = IdType.AUTO)
    private int id;
    private String no;
    private String color;
    private int size;
    private int amount;
    private int orderid;
    private int type;
    @TableLogic(value = "0",delval = "1")
    @TableField(select = false)
    private int deleted;
}

