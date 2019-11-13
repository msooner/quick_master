package com.ron.mapper;

import com.ron.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Ron
 * @data 2019/11/05
 */
@Mapper
public interface SystemUsersMapper {

    /**
     * 查询某个用户信息
     */
    SystemUser getUser();

    /**
     *  查询所有用户信息
     */
    List<SystemUser> getUsers();

    /**
     * 获取某个部门所有用户
     */
    List<SystemUser> getDepartmentUsers();
}
