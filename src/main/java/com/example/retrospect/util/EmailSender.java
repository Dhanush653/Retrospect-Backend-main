package com.example.retrospect.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    JavaMailSender sender;

    public void sendMail(String tomail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rohithv1396@gmail.com");
        message.setTo(tomail);
        message.setSubject(subject);
        message.setText(body);

        sender.send(message);

        System.out.println("Mail sent Successfully");
    }
}
