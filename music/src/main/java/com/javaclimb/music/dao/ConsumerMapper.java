package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Consumer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Mapper
@Repository
public interface ConsumerMapper {
    //增
    public int insert(Consumer consumer);

    //改
    public int update(Consumer consumer);

    //删
    public int delete(Integer id);

    //根据主键查询
    public Consumer select(Integer id);

    //查询所有用户
    public List<Consumer> selectAll();

    //验证密码
    public int verifyPassword(String username, String password);

    //根据账号查询
    public Consumer selectByUsername(String username);
}
