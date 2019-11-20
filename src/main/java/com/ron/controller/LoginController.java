package com.ron.controller;

import com.ron.common.constants.DigitConstant;
import com.ron.common.constants.StringConsant;
import com.ron.dto.ResponseResult;
import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import com.ron.utils.CookieUtil;
import com.ron.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 登录逻辑
 *
 * @author Ron
 * @date 2019/11/6
 */
@Controller
@RequestMapping
public class LoginController {

    @Autowired
    SystemUserService systemUserService;

    /**
     * 用户登录界面(默认为登录界面，如果已经登录则跳转至用户中心)
     *
     * @return String
     */
    @GetMapping({"/", "/login"})
    public String login(@CookieValue(name = "user", defaultValue = "") String userCookie) {
        //如果用户Cookie不存在，则生成
        if ("".equals(userCookie) || userCookie == null) {
            String randomString = StringUtil.getRandomString(10);
            String userMD5Cookie = StringUtil.getMD5String(randomString, "");
            CookieUtil.setCookie("user", userMD5Cookie);
        } else {
            //如果用户已登录，则跳转至后台首页
            SystemUser systemUser = systemUserService.getUserInfo(userCookie);
            if (systemUser != null) {
                return "page/index";
            }
        }

        return "page/login";
    }

    /**
     * 登录逻辑
     *
     * @param username
     * @param rememberMe
     * @param password
     * @param userCookie
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/loginResult")
    public ResponseResult loginResult(@RequestParam(value = "username", required = true) String username,
                                               @RequestParam(value = "rememberMe", defaultValue = "1") Integer rememberMe,
                                               @RequestParam(value = "password", required = true) String password,
                                               @CookieValue(name = "user", defaultValue = "") String userCookie) {
        //判断用户是否已经登录
        if (systemUserService.checkUserIsLogged(userCookie)) {
            return new ResponseResult(DigitConstant.ALREADY_LOGGED_ERROR, "", StringConsant.ALREADY_LOGGED_ERROR);
        }
        //加密密码
        String MD5Password = StringUtil.getMD5String(password, StringConsant.PASSWORD_SALT);
        //验证用户账户信息
        SystemUser sysUser = systemUserService.getLoginUser(username, MD5Password);
        if (sysUser == null) {
            return new ResponseResult(DigitConstant.USER_NAME_NOT_EXIST, "", StringConsant.USER_NAME_NOT_EXIST);
        }

        //缓存用户信息
        String userCacheKey = userCookie + String.valueOf(sysUser.getId());
        int cacheTime = DigitConstant.DEFAULT_CACHE_TIME;
        if (rememberMe == 1) {
            cacheTime = DigitConstant.REMEMBER_ME_CACHE_TIME;
        }
        systemUserService.setUserInfo(userCacheKey, sysUser, cacheTime);

        return new ResponseResult(DigitConstant.SUCCESS_LOGED, sysUser, StringConsant.SUCCESS_LOGED);
    }

    /**
     * 注册逻辑
     *
     * @param username
     * @param password
     * @param rePassword
     * @param email
     * @param userCookie
     * @return
     */
    @RequestMapping("/registerResult")
    public ResponseResult registerResult(@RequestParam(value = "username", required = true) String username,
                                         @RequestParam(value = "password", required = true) String password,
                                         @RequestParam(value = "rePassword", required = true) String rePassword,
                                         @RequestParam(value = "email", required = true) String email,
                                         @CookieValue("user") String userCookie) {

        //判断用户是否登录


        return new ResponseResult(DigitConstant.SUCCESS_LOGED, "", StringConsant.SUCCESS_LOGED);
    }
}
