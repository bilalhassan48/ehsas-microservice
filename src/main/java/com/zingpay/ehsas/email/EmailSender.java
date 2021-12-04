package com.zingpay.ehsas.email;

import com.zingpay.ehsas.config.EmailConfig;
import com.zingpay.ehsas.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@Component
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(EmailDto emailDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(EmailConfig.EMAIL_FROM);
        helper.setTo(emailDto.getTo());
        helper.setText(emailDto.getText());
        helper.setSubject(emailDto.getSubject());
        //helper.addAttachment("Logo", emailDto.getFile());

        javaMailSender.send(message);
    }
}
