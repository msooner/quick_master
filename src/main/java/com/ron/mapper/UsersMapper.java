package com.ron.mapper;

import com.ron.entity.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Ron
 * @data 2019/11/05
 */
@Mapper
public interface UsersMapper {

    /**
     * 查询某个用户信息
     */
    Users getUser();

    /**
     *  查询所有用户信息
     */
    List<Users> getUsers();

    /**
     * 获取某个部门所有用户
     */
    List<Users> getDepartmentUsers();
}
