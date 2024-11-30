package com.example.QuanLyPhongTro.services;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailService {

    private String smtpHost = "smtp.example.com"; // Thay bằng địa chỉ SMTP của bạn
    private String smtpPort = "587"; // Cổng SMTP
    private String username = "your-email@example.com"; // Tài khoản email
    private String password = "your-password"; // Mật khẩu email

    public void sendEmail(String to, String subject, String message) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setSmtpPort(Integer.parseInt(smtpPort));
            email.setHostName(smtpHost);
            email.setAuthentication(username, password);
            email.setSSLOnConnect(true);
            email.setFrom(username);
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(to);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}