package com.example.domain;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.springframework.boot.convert.DataSizeUnit;

@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private String password;
    private String username;
    private String role;
    @TableLogic(value = "0",delval = "1")
    @TableField(select = false)
    private int deleted;


}
