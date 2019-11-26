package com.ron.common.constants;

/**
 * 数字常量类
 */
public final class DigitConstant {

    //登录相关
    public static final int USER_COOKIE_ERROR = 6;
    public static final int USER_NAME_NOT_EXIST = 5;
    public static final int USER_NAME_ERROR = 4;
    public static final int PASSWORD_ERROR = 3;
    public static final int USERNAME_OR_PASSWORD_ERROR = 2;
    public static final int ALREADY_LOGGED_ERROR = 1;
    public static final int SUCCESS_LOGED = 0;

    //注册相关
    public static final int REPASSWORD_DIFFERENT = 1;
    public static final int USERNAME_ALREADLY_EXISTS = 2;
    public static final int REGISTER_FAIL = 3;
    public static final int EMAIL_ERROR = 4;
    public static final int USERNAME_OR_PASSWORD_INJECTION = 5;
    public static final int USERNAME_PASSWORD_SAME_ERROR = 6;

    //找回、重置密码相关
    public static final int ACCOUNT_NOEXISTS = 1;
    public static final int SEND_EMAIL_SUCCESS = 0;
    public static final int RESET_PASSWORD_SUCCESS = 0;
    public static final int RESET_PASSWORD_ERROR = 1;
    public static final int RESET_PASSWORD_FAIL = 2;
    //缓存时间
    public static final int DEFAULT_CACHE_TIME = 1800; //半个小时
    public static final int REMEMBER_ME_CACHE_TIME = 86400; //一天
}
