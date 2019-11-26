//package com.ron.utils;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.mail.MessagingException;
//
//import java.io.UnsupportedEncodingException;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class EmailUtilTest {
//
//    @Autowired
//    private EmailUtil emailUtil;
//
//    @Test
//    public void sendMail() throws Exception {
//
//        try {
//            // 发送普通文本邮件
//            emailUtil.sendMail("conan@jollycorp.com", "邮件发送测试", "<a href='https://www.baidu.com' >百度一下</a>");
//            // 发送带附件的邮件
//            //emailUtil.sendAttachmentMail("xxxxxxx@163.com", "邮件发送测试", "<a href='https://www.baidu.com' >百度一下</a>", "D:\\my_resources\\company_relate\\MobileFile\\IMG_2052.JPG");
//        }catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//}