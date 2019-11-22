package com.ron.utils;

import com.ron.config.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 发送邮件工具类
 *
 * @author Ron 2019/11/22
 */
@Configuration
public class EmailUtil {
    private static final String HOST = EmailConfig.getHost();
    private static final String USERNAME = EmailConfig.getUsername();
    private static final String PASSWORD = EmailConfig.getPassword();
    private static final String PROTOCOL = EmailConfig.getProtocol();
    private static final int PORT = EmailConfig.getPort();
    private static final String ENCODING = EmailConfig.getEncoding();
    private static final String TIMEOUT = EmailConfig.getTimeout();
    private static final String FROM = EmailConfig.getFrom();
    private static final String PERSONAL = EmailConfig.getPersonal();

    private static JavaMailSenderImpl mailSender = createMailSender();

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createMailSender() {
        System.out.println(HOST + "-" + PORT + "-" + USERNAME + "-" + PASSWORD);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding(ENCODING);

        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", TIMEOUT);
        p.setProperty("mail.smtp.auth", "false");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        sender.setJavaMailProperties(p);

        return sender;
    }

    /**
     * 发送邮件
     *
     * @param to      接受人
     * @param subject 主题
     * @param html    发送内容
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendMail(String to, String subject, String html) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(FROM, PERSONAL);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to      接受人
     * @param subject 主题
     * @param html    发送内容
     * @param filePath  附件路径
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendAttachmentMail(String to, String subject, String html, String filePath) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(FROM, PERSONAL);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        FileSystemResource file=new FileSystemResource(new File(filePath));
        String fileName=filePath.substring(filePath.lastIndexOf(File.separator));
        messageHelper.addAttachment(fileName,file);
        mailSender.send(mimeMessage);
    }
}
