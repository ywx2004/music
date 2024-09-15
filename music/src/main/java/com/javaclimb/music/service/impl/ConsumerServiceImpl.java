package com.javaclimb.music.service.impl;

import com.javaclimb.music.domain.Consumer;
import com.javaclimb.music.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javaclimb.music.dao.ConsumerMapper;

import java.util.List;

//用户service实现类
@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerMapper consumerMapper;


    @Override
    public boolean insert(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }

    @Override
    public boolean update(Consumer consumer) {
        return consumerMapper.update(consumer) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return consumerMapper.delete(id) > 0;
    }

    @Override
    public Consumer select(Integer id) {
        return consumerMapper.select(id);
    }

    @Override
    public List<Consumer> selectAll() {
        return consumerMapper.selectAll();
    }

    @Override
    public boolean verifyPassword(String username, String password) {
        return consumerMapper.verifyPassword(username,password) > 0;
    }

    @Override
    public Consumer selectByUsername(String username) {
        return consumerMapper.selectByUsername(username);
    }
}
