package com.zingpay.ehsas.util;

import org.springframework.http.HttpStatus;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

public enum StatusMessage {
    FAILURE(0,"FAILURE", HttpStatus.NOT_FOUND),
    SUCCESS(1,"SUCCESS", HttpStatus.OK),

    CUSTOMER_DOES_NOT_EXIST(-1, "Customer not exist", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_OTP(-2, "Please enter valid otp", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AMOUNT_ENTERED(-3, "Entered amount is invalid", HttpStatus.INTERNAL_SERVER_ERROR),

    ;

    private StatusMessage(int id, String description, HttpStatus statusCode) {
        this.id = id;
        this.description = description;
        this.statusCode = statusCode;
    }
    private int id;
    private String description;
    private HttpStatus statusCode;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
