package com.zingpay.ehsas.dto;

import lombok.Data;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@Data
public class SmsDto {
    private String to;
    private String text;

}
