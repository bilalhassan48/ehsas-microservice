package com.zingpay.ehsas.service;

import com.zingpay.ehsas.dto.FetchProductRequestDto;
import com.zingpay.ehsas.dto.ProductDto;
import com.zingpay.ehsas.dto.TransactionDto;
import com.zingpay.ehsas.entity.Customer;
import com.zingpay.ehsas.entity.Product;
import com.zingpay.ehsas.entity.Transaction;
import com.zingpay.ehsas.repository.CustomerRepository;
import com.zingpay.ehsas.repository.ProductRepository;
import com.zingpay.ehsas.util.Status;
import com.zingpay.ehsas.util.StatusMessage;
import com.zingpay.ehsas.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionService transactionService;

    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Status fetchDiscountedPrice(FetchProductRequestDto fetchProductRequestDto) {
        if(validateTotalAmount(fetchProductRequestDto)) {
            Customer savedCustomer = saveNewCustomer(fetchProductRequestDto);
            fetchProductRequestDto.setCustomerId(savedCustomer.getId());
            Transaction savedTransaction = transactionService.savePendingTransaction(fetchProductRequestDto);
            return new Status(StatusMessage.SUCCESS, Transaction.convertToDto(savedTransaction));
        } else {
            return new Status(StatusMessage.INVALID_AMOUNT_ENTERED);
        }
    }

    private boolean validateTotalAmount(FetchProductRequestDto fetchProductRequestDto) {
        double totalAmount = 0;
        for (ProductDto productDto : fetchProductRequestDto.getProductDtos()) {
            double totalamountPerProduct = productDto.getPrice() * productDto.getQuantity();
            if(totalamountPerProduct != productDto.getTotalPrice()) {
                return false;
            } else {
                totalAmount += productDto.getTotalPrice();
            }
        }
        if(totalAmount != fetchProductRequestDto.getTotalAmount()) {
            return false;
        }
        return true;
    }

    private Customer saveNewCustomer(FetchProductRequestDto fetchProductRequestDto) {
        Customer customer = new Customer();
        customer.setMobile(fetchProductRequestDto.getMobile());
        customer.setCnic(fetchProductRequestDto.getCnic());
        customer.setOtp(Utils.generateFourDigitPin()+"");
        return customerRepository.save(customer);
    }
}
