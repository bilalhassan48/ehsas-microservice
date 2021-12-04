package com.zingpay.ehsas.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Getter
@Setter
public class FetchProductRequestDto {
    private String cnic;
    private String mobile;
    private int customerId;
    private int accountId;
    private double totalAmount;
    private double totalDiscountAmount;
    private List<ProductDto> productDtos;
}
