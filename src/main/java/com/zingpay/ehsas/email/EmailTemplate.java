package com.zingpay.ehsas.email;

import com.zingpay.ehsas.dto.EmailDto;
import com.zingpay.ehsas.util.Utils;
import org.springframework.stereotype.Component;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@Component
public class EmailTemplate {

    /*public EmailDto createResetPasswordEmail(AppUser appUser) {
        String decodedPassword = "";
        if(appUser.getPassword() != null && !appUser.getPassword().equals("")) {
            decodedPassword = Utils.decodePassword(appUser.getPassword());
        }
        String to = appUser.getEmail();
        String text = "Hi " + appUser.getFullName() + ",\nYour password has been reset from back office, please use :" + decodedPassword + " to login";
        String subject = "Welcome to Zing Pay";
        //FileSystemResource file;
        EmailDto emailDto = new EmailDto();
        emailDto.setTo(to);
        emailDto.setText(text);
        emailDto.setSubject(subject);
        //emailDto.setFile(file);
        return emailDto;
    }

    public EmailDto createKycEmail(AppUser appUser) {
        String to = appUser.getEmail();
        String text = "Hi " + appUser.getFullName();
        if(appUser.getKycStatusId() == KycStatus.APPROVE.getId()) {
            text = text + ",\nYour account has been approved from back office.";
        } else if(appUser.getKycStatusId() == KycStatus.REJECT.getId()) {
            text = text + ",\nYour account has been rejected from back office due to following reason : \n" + appUser.getKycDescription();
        }
        String subject = "Welcome to Zing Pay";
        //FileSystemResource file;
        EmailDto emailDto = new EmailDto();
        emailDto.setTo(to);
        emailDto.setText(text);
        emailDto.setSubject(subject);
        //emailDto.setFile(file);
        return emailDto;
    }*/

}
