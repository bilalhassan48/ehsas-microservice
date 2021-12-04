package com.zingpay.ehsas.sms;


import com.zingpay.ehsas.dto.SmsDto;
import com.zingpay.ehsas.util.Utils;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

/**
 * @author Bilal Hassan on 16-Nov-20
 * @project back-office-microservice
 */

@Component
public class SmsSender {

    public void sendSms(SmsDto smsDto) {
        StringBuilder url = new StringBuilder("https://connect.jazzcmt.com/sendsms_url.html?Username=03012448757&Password=Jazz@123&From=Business&To=");
        url.append(smsDto.getTo());
        url.append("&Message=");
        //url.append(smsDto.getText());
        url.append(URLEncoder.encode(smsDto.getText()));
        Utils.executeURL(url.toString(), "", "GET", "", "");
    }
}
