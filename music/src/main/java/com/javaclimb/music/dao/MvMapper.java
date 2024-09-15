package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Mv;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// MV DAO
@Mapper
@Repository
public interface MvMapper {

    // 增
    int insert(Mv mv);

    // 改
    int update(Mv mv);

    // 删
    int delete(Integer id);

    // 根据主键查询 MV
    Mv select(Integer id);

    // 查询所有 MV
    List<Mv> selectAll();

    // 根据名称模糊查询 MV
    List<Mv> selectByName(String name);
}
