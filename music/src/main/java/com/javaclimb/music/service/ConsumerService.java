package com.javaclimb.music.service;

//用户service接口

import com.javaclimb.music.domain.Consumer;

import java.util.List;

public interface ConsumerService {
    //增
    public boolean insert(Consumer consumer);

    //改
    public boolean update(Consumer consumer);

    //删
    public boolean delete(Integer id);

    //根据主键查询
    public Consumer select(Integer id);

    //查询所有用户
    public List<Consumer> selectAll();

    //验证密码
    public boolean verifyPassword(String username, String password);

    //根据账号查询
    public Consumer selectByUsername(String username);



}


