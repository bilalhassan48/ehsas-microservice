package com.zingpay.ehsas.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zingpay.ehsas.entity.Transaction;
import com.zingpay.ehsas.util.TransactionStatus;
import com.zingpay.ehsas.util.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Getter
@Setter
public class TransactionDto {
    private int id;
    private int customerId;
    private ArrayNode detailsJson;
    private TransactionStatus transactionStatus;
    private String retailerRefNumber;
    private String description;
    private Date createdOn;
    private Date updatedOn;
    private int accountId;
    private double totalAmount;
    private double discountedAmount;

    public static Transaction convertToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setCustomerId(transactionDto.getCustomerId());
        try {
            transaction.setDetailsJson(Utils.parseObjectToJson(transactionDto.getDetailsJson()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(transactionDto.getTransactionStatus() != null)
            transaction.setStatusId(transactionDto.getTransactionStatus().getId());
        transaction.setRetailerRefNumber(transactionDto.getRetailerRefNumber());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setCreatedOn(transactionDto.getCreatedOn());
        transaction.setUpdatedOn(transactionDto.getUpdatedOn());
        transaction.setAccountId(transactionDto.getAccountId());
        transaction.setTotalAmount(transactionDto.getTotalAmount());
        transaction.setDiscountedAmount(transactionDto.getDiscountedAmount());

        return transaction;
    }

    public static List<Transaction> convertToEntity(List<TransactionDto> transactionDtos) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactionDtos.forEach(transactionDto -> {
            transactions.add(convertToEntity(transactionDto));
        });

        return transactions;
    }
}
