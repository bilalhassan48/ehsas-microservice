package com.zingpay.ehsas.controller;

import com.zingpay.ehsas.dto.CustomerDto;
import com.zingpay.ehsas.dto.TransactionDto;
import com.zingpay.ehsas.entity.Customer;
import com.zingpay.ehsas.entity.Transaction;
import com.zingpay.ehsas.service.CustomerService;
import com.zingpay.ehsas.service.SmsService;
import com.zingpay.ehsas.service.TransactionService;
import com.zingpay.ehsas.util.Status;
import com.zingpay.ehsas.util.StatusMessage;
import com.zingpay.ehsas.util.TransactionStatus;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Bilal Hassan on 28-Nov-21
 * @project ehsas-integration-microservice
 */

@RestController
@RequestMapping("/customer")
@Api(value = "customer", description = "Contains methods related to customer.")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SmsService smsService;

    @PutMapping("/send-otp")
    public Status verifyOtp(@RequestBody TransactionDto transactionDto) {
        //validate total amount here
        transactionDto.setAccountId(getLoggedInUserAccountId());
        Optional<Customer> customerOptional = customerService.getById(transactionDto.getCustomerId());
        if(customerOptional.isPresent()) {
            smsService.createFetchDiscountedPriceSms(customerOptional.get());
            return new Status(StatusMessage.SUCCESS, Transaction.convertToDto(transactionService.saveTransaction(transactionDto, TransactionStatus.PRE_OTP_VERIFICATION)));
        } else {
            return response(StatusMessage.CUSTOMER_DOES_NOT_EXIST);
        }
    }

    @PutMapping("/verify-otp")
    public Status verifyOtp(@RequestParam String otp, @RequestBody TransactionDto transactionDto) {
        transactionDto.setAccountId(getLoggedInUserAccountId());
        Optional<Customer> customerOptional = customerService.getById(transactionDto.getCustomerId());
        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if(customer.getOtp().equals(otp)) {
                return new Status(StatusMessage.SUCCESS, Transaction.convertToDto(transactionService.saveTransaction(transactionDto, TransactionStatus.POST_OTP_VERIFICATION)));
                //call third party api here and update transaction status
            } else {
                return response(StatusMessage.INVALID_OTP);
            }
        } else {
            return response(StatusMessage.CUSTOMER_DOES_NOT_EXIST);
        }
    }
}
