package com.zingpay.ehsas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zingpay.ehsas.dto.FetchProductRequestDto;
import com.zingpay.ehsas.dto.ProductDto;
import com.zingpay.ehsas.dto.TransactionDto;
import com.zingpay.ehsas.entity.Transaction;
import com.zingpay.ehsas.repository.TransactionRepository;
import com.zingpay.ehsas.util.TransactionStatus;
import com.zingpay.ehsas.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction saveTransaction(TransactionDto transactionDto, TransactionStatus transactionStatus) {
        Transaction transaction = TransactionDto.convertToEntity(transactionDto);
        transaction.setUpdatedOn(new Date());
        transaction.setStatusId(transactionStatus.getId());
        return transactionRepository.save(transaction);
    }

    public Transaction savePendingTransaction(FetchProductRequestDto fetchProductRequestDto) {
        Transaction transaction = new Transaction();

        transaction.setCustomerId(fetchProductRequestDto.getCustomerId());
        transaction.setCreatedOn(new Date());
        transaction.setAccountId(fetchProductRequestDto.getAccountId());
        transaction.setDescription("");
        transaction.setStatusId(TransactionStatus.PRE_OTP_VERIFICATION.getId());
        try {
            transaction.setDetailsJson(Utils.parseObjectToJson(fetchProductRequestDto.getProductDtos()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        transaction.setTotalAmount(fetchProductRequestDto.getProductDtos().stream().mapToDouble(ProductDto::getTotalPrice).sum());

        AtomicReference<Double> totalDiscount = new AtomicReference<>((double) 0);

        fetchProductRequestDto.getProductDtos().forEach(productDto -> {
            double discount = (productDto.getTotalPrice() * 30) / 100;
            productDto.setDiscount(discount);
            totalDiscount.updateAndGet(v -> new Double((double) (v + discount)));
        });

        transaction.setDiscountedAmount(totalDiscount.get());

        return transactionRepository.save(transaction);
    }
}
