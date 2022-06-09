package com.example.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)
    private int id;
    private String no;
    private String name;
    private String color;
    private String size;
    private int amount;
    private int orderid;
    private int storeid;
    private String type;
    @TableLogic(value = "0",delval = "1")
    @TableField(select = false)
    private String deleted;
}
