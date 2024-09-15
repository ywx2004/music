package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.MvMapper;
import com.javaclimb.music.domain.Mv;
import com.javaclimb.music.service.MvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// MV Service 实现类
@Service
public class MvServiceImpl implements MvService {

    @Autowired
    private MvMapper mvMapper;

    @Override
    public boolean insert(Mv mv) {
        return mvMapper.insert(mv) > 0;
    }

    @Override
    public boolean update(Mv mv) {
        return mvMapper.update(mv) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return mvMapper.delete(id) > 0;
    }

    @Override
    public Mv select(Integer id) {
        return mvMapper.select(id);
    }

    @Override
    public List<Mv> selectAll() {
        return mvMapper.selectAll();
    }

    @Override
    public List<Mv> selectByName(String name) {
        return mvMapper.selectByName(name);
    }
}

