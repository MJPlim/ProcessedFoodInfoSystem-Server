package com.plim.plimserver.global.domain.mail.util;

import com.plim.plimserver.global.domain.mail.domain.EmailSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String toAddress, EmailSubject subject, String body) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAddress);
            helper.setSubject(subject.getSubject());
            helper.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

}
