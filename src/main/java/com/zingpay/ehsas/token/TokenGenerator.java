package com.zingpay.ehsas.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zingpay.ehsas.dto.LoginResponse;
import com.zingpay.ehsas.feign.AuthServiceClient;
import com.zingpay.ehsas.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@Component("TokenGenerator")
public class TokenGenerator {

    @Autowired
    private AuthServiceClient authServiceClient;

    public static String token;

    public String getTokenFromAuthService() throws JsonProcessingException {
        String abc = "";
        boolean retry = true;
        long retryIn = 0;
        while(retry) {
            try {
                Thread.sleep(retryIn);
                abc = authServiceClient.login("client_credentials");
                retry = false;
                retryIn = 0;
            } catch (Exception e) {
                retry = true;
                if(retryIn < 30000) {
                    retryIn += 5000;
                }
            }
        }
        LoginResponse loginResponse = Utils.parseToObject(abc, LoginResponse.class);
        return this.token = "Bearer " + loginResponse.getAccessToken();
    }
}
