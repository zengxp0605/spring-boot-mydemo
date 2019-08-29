package com.jason.mybatisxml.controller;

import com.jason.mybatisxml.mapper.UserMapper;
import com.jason.mybatisxml.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试事务回滚
     * @return
     */
    @RequestMapping("/testTrans")
    @Transactional(propagation = Propagation.REQUIRED)
    public String testTrans() {
        User user = new User();
        user.setUserName("tran-nickname");
        int effRows =  userMapper.insert(user);

        // 模拟抛出异常
        int a = 1 / 0;

        user.setPassWord("123456");
        userMapper.update(user);
        return "TestTrans end: " + user.getId() + " --- " + effRows;
    }


    @RequestMapping("/")
    public List<User> home() {
        return this.getUsers();
    }

    @RequestMapping("/getUsers")
    public List<User> getUsers() {
        List<User> users = userMapper.getAll();
        return users;
    }

    @RequestMapping("/getUser")
    public User getUser(Long id) {
        User user = userMapper.getOne(id);
        return user;
    }

    @RequestMapping("/add")
    public void save(User user) {
        userMapper.insert(user);
    }

    @RequestMapping(value = "update")
    public void update(User user) {
        userMapper.update(user);
    }

    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userMapper.delete(id);
    }

}

