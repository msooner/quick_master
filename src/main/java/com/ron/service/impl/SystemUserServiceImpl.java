package com.ron.service.impl;

import com.ron.entity.SystemUser;
import com.ron.mapper.SystemUsersMapper;
import com.ron.service.SystemUserService;
import com.ron.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther Ron
 * @date 2019/10/6
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //设置盐值字符串，随便定义，用于混淆MD5值
    private final String salt = "sjajaspu-i-2jrfm;sd";
    //设置秒杀redis缓存的key
    private final String key = "seckill";

    @Autowired
    private SystemUsersMapper systemUsersMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 从redis缓存中获取用记信息
     *
     * @param userCookie
     * @return
     */
    @Override
    public SystemUser getUserInfo(String userCookie) {
        if ("".equals(userCookie) || userCookie == null) {
            return null;
        }
        //从redis中获取用户信息
        SystemUser systemUser = (SystemUser) redisUtil.get(userCookie);

        return systemUser;
    }

    /**
     * 将用户信息存入redis中，以userCookie作为键
     */
    public void setUserInfo() {

    }

    /**
     * 查询所有用户信息
     */
    @Override
    public List<SystemUser> getAllUsers() {
        return null;
    }

    @Override
    public SystemUser getUserById(int userId) {
        return systemUsersMapper.getUser(userId);
    }

    /**
     * 获取登录用户信息
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public SystemUser getLoginUser(String userName, String password) {
        return null;
    }

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @Override
    public void addUser(SystemUser user) {

    }

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public void deleteUser(Integer userId) {

    }

    /**
     * 编辑用户信息
     *
     * @param user
     * @return
     */
    @Override
    public void editUser(SystemUser user) {

    }


}
