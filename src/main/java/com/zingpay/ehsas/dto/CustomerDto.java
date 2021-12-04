package com.zingpay.ehsas.dto;

import com.zingpay.ehsas.entity.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Getter
@Setter
public class CustomerDto {
    private int id;
    private String mobile;
    private String cnic;
    private String otp;

    public static Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setMobile(customerDto.getMobile());
        customer.setCnic(customerDto.getCnic());
        customer.setOtp(customerDto.getOtp());

        return customer;
    }

    public static List<Customer> convertToEntity(List<CustomerDto> customerDtos) {
        List<Customer> customers = new ArrayList<Customer>();
        customerDtos.forEach(customerDto -> {
            customers.add(convertToEntity(customerDto));
        });

        return customers;
    }
}
