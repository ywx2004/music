package com.javaclimb.music.service;

import com.javaclimb.music.domain.Mv;
import java.util.List;

// MV Service 接口
public interface MvService {

    // 增
    boolean insert(Mv mv);

    // 改
    boolean update(Mv mv);

    // 删
    boolean delete(Integer id);

    // 根据主键查询
    Mv select(Integer id);

    // 查询所有 MV
    List<Mv> selectAll();

    // 根据名称模糊查询
    List<Mv> selectByName(String name);
}

