package com.ron.common.resolver;

import com.ron.utils.StringUtil;
import com.sun.corba.se.spi.resolver.LocalResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 自定义国际化解析器：实现LocaleResolver接口
 *
 * @author Ron 2019/11/27
 */
public class MyLocaleResolver implements LocaleResolver{

    /**
     * 自定义国际化解析器：如果请求参数中有lang参数，则使用自定义的国际化解析器，否则使用SpringBoot默认的国际化解析器
     *
     * @param httpServletRequest request请求对象
     * @return locale
     */
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {

        //设置默认的locale信息
        Locale locale = Locale.getDefault();
        //如果参数传入locale信息，则重新设置
        String lang = httpServletRequest.getParameter("lang");
        if (! StringUtils.isEmpty(lang)) {
            String[] langArray = lang.split("_");
            locale = new Locale(langArray[0], langArray[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse, @Nullable Locale locale) {

    }
}
