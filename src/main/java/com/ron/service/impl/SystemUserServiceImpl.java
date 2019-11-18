package com.ron.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ron.entity.SystemUser;
import com.ron.mapper.SystemUserMapper;
import com.ron.service.SystemUserService;
import com.ron.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther Ron
 * @date 2019/10/6
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemUserMapper systemUsersMapper;

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
        return (SystemUser) redisUtil.get(userCookie);
    }

    /**
     * 将用户信息存入redis缓存
     *
     * @param userCacheKey
     * @param systemUser
     * @param cacheTime
     */
    @Override
    public void setUserInfo(String userCacheKey, SystemUser systemUser, int cacheTime) {
        if (userCacheKey != null && userCacheKey.length() > 0 && systemUser != null) {
            redisUtil.set(userCacheKey, systemUser, cacheTime);
        }
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
     * @param username
     * @param password
     * @return
     */
    @Override
    public SystemUser getLoginUser(String username, String password) {
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
