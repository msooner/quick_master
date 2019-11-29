package com.ron.service.impl;

import com.alibaba.fastjson.JSON;
import com.ron.entity.SystemUser;
import com.ron.mapper.SystemUserMapper;
import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import com.ron.utils.RedisUtil;
import com.ron.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Object getUserInfo(String userCookie) {
        if (StringUtils.isEmpty(userCookie)) {
            return null;
        }
        //从redis中获取用户信息
        Object o = redisUtil.get(userCookie);

        return o == null ? null : JSON.parseObject(o.toString(), SystemUser.class);
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
        if (! StringUtils.isEmpty(userCacheKey) && systemUser.getId() > 0) {
            String jsonUser = JSON.toJSONString(systemUser);
            redisUtil.set(userCacheKey, jsonUser, cacheTime);
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
        return systemUsersMapper.getLoginUser(username, password);
    }

    @Override
    public void registerUser(SystemUser systemUser) {
        systemUsersMapper.registerUser(systemUser);
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

    /**
     * 判断用户是否已经登录
     *
     * @param userCookie
     * @return
     */
    public boolean checkUserIsLogged(String userCookie) {
        if (StringUtils.isEmpty(userCookie)) {
            return false;
        }
        SystemUser systemUser = (SystemUser) this.getUserInfo(userCookie);
        if (systemUser != null && systemUser.getId() > 0) {
            return true;
        }

        return false;
    }

    /**
     * 检测用户是否已经存在
     *
     * @param username
     * @param email
     * @return
     */
    @Override
    public boolean checkRegisterSystemUser(String username, String email) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {
            return false;
        }
        SystemUser systemUser = systemUsersMapper.checkRegisterSystemUser(username, email);
        if (systemUser != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateForgotPassword(String username, String email, String password) {
        if (username.length() > 0 && email.length() > 0 && password.length() > 0) {
            return systemUsersMapper.updateForgotPassword(username, email, password);
        }

        return false;
    }

    @Override
    public void logout(String userCookie) {
        if (! StringUtils.isEmpty(userCookie)) {
            //清除redis
            redisUtil.deleteKey(userCookie);
            //清除cookie
            CookieUtil.removeCookie(userCookie);
        }
    }

}
