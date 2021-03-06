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
    public static final int USER_IS_LOCKED = 7;

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

    //管理员相关提示
    public static final int ADD_ADMIN_SUCCESS = 0;
    public static final int ADD_ADMIN_INFO_ERROR = 1;

    public static final int MANAGER_USERNAME_ERROR = 2;

    public static final int EDIT_ADMIN_SUCCESS = 0;
    public static final int EDIT_ADMIN_INFO_ERROR = 1;

    public static final int DELETE_ADMIN_INFO_SUCCESS = 0;
    public static final int DELETE_CURRENT_USER_ERROR = 1;
    public static final int DELETE_ADMIN_INFO_FAIL = 2;

    //部门相关
    public static final int DELETE_DEPARTMENT_SUCCESS = 0;
    public static final int DELETE_DEPARTMENT_FAIL = 1;
    public static final int ADD_DEPARTMENT_SUCCESS = 0;
    public static final int ADD_DEPARTMENT_INFO_ERROR = 2;
    public static final int DEPARTMENT_INFO_ISEXISTS = 3;
    public static final int ADD_DEPARTMENT_FAIL = 1;
    public static final int EDIT_DEPARTMENT_SUCCESS = 0;
    public static final int EDIT_DEPARTMENT_INFO_ERROR = 2;
    public static final int DEPARTMENT_INFO_NOT_EXISTS = 3;
    public static final int EDIT_DEPARTMENT_FAIL = 1;

    //角色相关
    public static final int ADD_ROLE_SUCCESS = 0;
    public static final int ADD_ROLE_INFO_ERROR = 1;
    public static final int ROLE_INFO_IS_EXISTS = 2;
    public static final int ROLE_INFO_IS_NOT_EXISTS = 4;
    public static final int ADD_ROLE_FAIL = 3;
    public static final int GET_ROLE_LIST_SUCCESS = 0;
    public static final int EDIT_ROLE_SUCCESS = 0;
    public static final int DELETE_ROLE_SUCCESS = 0;
    public static final int EDIT_ROLE_FAIL = 2;
    public static final int DELETE_ROLE_FAIL = 2;
    public static final int EDIT_ROLE_NAME_ERROR = 3;

    public static final int AUTHORIZATION_SUCCESS = 0;
    public static final int AUTHORIZATION_FAIL = 1;

    //模块相关
    public static final int ADD_MODULE_SUCCESS = 0;
    public static final int ADD_MODULE_INFO_ERROR = 1;
    public static final int MODULE_INFO_IS_EXISTS = 2;
    public static final int ADD_MODULE_FAIL = 3;
    public static final int GET_MODULE_LIST_SUCCESS = 0;
    public static final int DELETE_MODULE_SUCCESS = 0;
    public static final int DELETE_MODULE_FAIL = 2;
    public static final int MODULE_INFO_NOT_EXISTS = 3;

    //参数相关
    public static final int REQUEST_PARAMETER_ERROR = 9;
    public static final int ILLEGAL_OPERATION = 10;

    //缓存时间
    public static final int DEFAULT_CACHE_TIME = 1800; //半个小时
    public static final int REMEMBER_ME_CACHE_TIME = 86400; //一天

    //系统管理相关
    public static final int SYSTEM_ROLE_ID = 1;
}
