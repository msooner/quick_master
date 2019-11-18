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

    @ResponseBody
    @PostMapping(value = "/loginResult", produces = "application/json")
    public ResponseResult<Integer> loginResult(@RequestParam(value = "username", required = true) String username,
                                               @RequestParam(value = "rememberMe", defaultValue = "1") Integer rememberMe,
                                               @RequestParam(value = "password", required = true) String password,
                                               @CookieValue(name = "user", defaultValue = "") String userCookie) {
        //检测用户cookie信息
        if ("".equals(userCookie)) {
            return new ResponseResult(DigitConstant.USER_COOKIE_ERROR, "", StringConsant.USER_COOKIE_ERROR);
        }
        //判断是否重复登录
        SystemUser systemUser = systemUserService.getUserInfo(userCookie);
        if (systemUser.getId() > 0) {
            return new ResponseResult(DigitConstant.ALREADY_LOGGED_ERROR, systemUser, StringConsant.ALREADY_LOGGED_ERROR);
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
        systemUserService.setUserInfo(userCacheKey, systemUser, cacheTime);

        return new ResponseResult(DigitConstant.SUCCESS_LOGED, sysUser, StringConsant.SUCCESS_LOGED);
    }
}
