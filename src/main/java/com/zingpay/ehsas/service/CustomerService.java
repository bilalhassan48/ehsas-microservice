package com.zingpay.ehsas.service;

import com.zingpay.ehsas.entity.Customer;
import com.zingpay.ehsas.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> getById(int id) {
        return customerRepository.findById(id);
    }
}
