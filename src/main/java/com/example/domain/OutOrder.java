package com.example.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@TableName("outorder")
public class OutOrder {
    @TableId(type = IdType.AUTO)
    private int id;
    private String no;
    private String store;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String role;
    private String origin;
    private String remarks;
    @TableLogic(value = "0",delval = "1")
    @TableField(select = false)
    private int deleted;




}
