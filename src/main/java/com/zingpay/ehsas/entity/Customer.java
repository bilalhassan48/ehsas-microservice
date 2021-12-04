package com.zingpay.ehsas.entity;

import com.zingpay.ehsas.dto.CustomerDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "cnic")
    private String cnic;

    @Column(name = "otp")
    private String otp;

    public static CustomerDto convertToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setMobile(customer.getMobile());
        customerDto.setCnic(customer.getCnic());
        customerDto.setOtp(customer.getOtp());

        return customerDto;
    }

    public static List<CustomerDto> convertToDto(List<Customer> customers) {
        List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
        customers.forEach(customer -> {
            customerDtos.add(convertToDto(customer));
        });

        return customerDtos;
    }
}
