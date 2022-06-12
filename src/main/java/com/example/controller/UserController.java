package com.example.controller;


import com.example.domain.Code;
import com.example.domain.Login;
import com.example.domain.Result;
import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;


    @PostMapping("/login")
    //登录，根据用户账户与密码查询数据库
    public Result login(@RequestBody User user) {

        User login = userService.login(user);
        System.out.println(login);
        int code = login != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = login != null ? "" : "ERROR";

        return new Result(code, login, msg);
    }

    @GetMapping("/userid")
    public Result userid(){
        int logininfo = Integer.parseInt(userService.logininfo());


        System.out.println(logininfo);

        User user = userService.selectById(logininfo);

        int code = user != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = user != null ? "" : "ERROR";
        return new Result(code, user, msg);
    }


    @PostMapping("/email")
    public Result loginByEmail(@RequestBody Login login) {
        String trueCode = userService.loginByEmail(login.getEmail());
        int code = trueCode != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = trueCode != null ? "" : "ERROR";
        return new Result(code, trueCode, msg);
    }

    @PostMapping("/checkcode")
    public Result checkCode(@RequestBody Login login) {
        boolean flag = userService.checkCode(login.getEmail(), login.getCheckcode());
        int code = flag != false ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }


    @GetMapping
    //查询所有
    public Result selectAll() {
        List<User> list = userService.list();
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }

    @PostMapping("/condition")
    public Result selectByCondition(@RequestBody User user){
        List<User> list = userService.selectByCondition(user);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);
    }


    @GetMapping("/name/{name}")
    //根据名字查询
    public Result selectByName(@PathVariable String name) {
        List<User> list = userService.selectByName(name);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }

    @GetMapping("/username/{name}")
    //根据账户号查询
    public Result selectByUserName(@PathVariable String name) {
        List<User> list = userService.selectByUserName(name);
        int code = list != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = list != null ? "" : "ERROR";
        return new Result(code, list, msg);

    }


    @GetMapping("/{id}")
    //根据id查询
    public Result selectById(@PathVariable int id) {
        User user = userService.selectById(id);
        int code = user != null ? Code.SELECT_OK : Code.SELECT_ERR;
        String msg = user != null ? "" : "ERROR";
        return new Result(code, user, msg);
    }

    @PutMapping
    //修改密码
    public Result updatePassword(@RequestBody User user) {
        boolean flag = userService.updateById(user);
        int code = flag != false ? Code.UPDATE_OK : Code.UPDATE_ERR;
        String msg = flag != false ? "" : "ERROR";

        return new Result(code, flag, msg);
    }



    @DeleteMapping("/{id}")
    //逻辑删除
    public Result delete(@PathVariable int id) {
        boolean flag = userService.removeById(id);
        int code = flag != false ? Code.DELETE_OK : Code.DELETE_ERR;
        String msg = flag != false ? "" : "ERROR";

        return new Result(code, flag, msg);
    }

    @PostMapping
    //新增
    public Result save(@RequestBody User user) {
        boolean flag = userService.save(user);
        int code = flag != false ? Code.SAVE_OK : Code.SAVE_ERR;
        String msg = flag != false ? "" : "ERROR";
        return new Result(code, flag, msg);
    }


}
