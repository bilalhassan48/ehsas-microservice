package com.zingpay.ehsas.sms;

import com.zingpay.ehsas.dto.SmsDto;
import com.zingpay.ehsas.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * @author Bilal Hassan on 16-Nov-20
 * @project back-office-microservice
 */

@Component
public class SmsTemplate {

    /*public SmsDto createResetPasswordSms(AppUser appUser) {
        String decodedPassword = "";
        if(appUser.getPassword() != null && !appUser.getPassword().equals("")) {
            decodedPassword = Utils.decodePassword(appUser.getPassword());
        }
        String to = appUser.getCellPhone();
        String text = "<#>Hi " + appUser.getFullName() + ",\nYour password has been reset from back office, please use :" + decodedPassword + " to login";
        SmsDto smsDto = new SmsDto();
        smsDto.setTo(to);
        smsDto.setText(text);
        return smsDto;
    }*/

    public SmsDto createFetchDiscountedPriceSms(Customer customer) {
        String to = customer.getMobile();
        String text = "<#>Hi,\nPlease use :" + customer.getOtp() + " to get discount";

        SmsDto smsDto = new SmsDto();
        smsDto.setTo(to);
        smsDto.setText(text);
        return smsDto;
    }
}
