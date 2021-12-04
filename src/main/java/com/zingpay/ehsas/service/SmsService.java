package com.zingpay.ehsas.service;

import com.zingpay.ehsas.dto.SmsDto;
import com.zingpay.ehsas.entity.Customer;
import com.zingpay.ehsas.sms.SmsSender;
import com.zingpay.ehsas.sms.SmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Service
public class SmsService {

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private SmsSender smsSender;

    @Async
    public void createFetchDiscountedPriceSms(Customer customer) {
        SmsDto smsDto = smsTemplate.createFetchDiscountedPriceSms(customer);
        smsSender.sendSms(smsDto);
    }
}
