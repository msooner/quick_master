package com.ron.service.impl;

import com.ron.entity.SystemUser;
import com.ron.service.SystemUserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @auther Ron
 * @date 2019/10/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SystemUserServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemUserService systemUserService;

    @Test
    public void findAll() {
        SystemUser systemUser = systemUserService.getUserById(1);
        logger.info("all={}", systemUser);
    }

    @Test
    public void testTime() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toDateTime());

        DateTime createDate = new DateTime("yyyy-MM-dd hh:mm:ss.SSSa");
        System.out.println(createDate);
    }

}