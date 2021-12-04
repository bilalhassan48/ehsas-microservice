package com.zingpay.ehsas.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zingpay.ehsas.dto.TransactionDto;
import com.zingpay.ehsas.util.TransactionStatus;
import com.zingpay.ehsas.util.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "details_json")
    private String detailsJson;

    @Column(name = "status_id")
    private long statusId;

    @Column(name = "retailer_ref_number")
    private String retailerRefNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "account_id")
    private int accountId;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "discounted_amount")
    private double discountedAmount;

    public static TransactionDto convertToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setCustomerId(transaction.getCustomerId());
        try {
            transactionDto.setDetailsJson(Utils.parseToObject(transaction.getDetailsJson(), ArrayNode.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(transaction.getStatusId() == TransactionStatus.SUCCESS.getId()) {
            transactionDto.setTransactionStatus(TransactionStatus.SUCCESS);
        } else if(transaction.getStatusId() == TransactionStatus.FAILED.getId()) {
            transactionDto.setTransactionStatus(TransactionStatus.FAILED);
        } else if(transaction.getStatusId() == TransactionStatus.PENDING.getId()) {
            transactionDto.setTransactionStatus(TransactionStatus.PENDING);
        } else if(transaction.getStatusId() == TransactionStatus.PRE_OTP_VERIFICATION.getId()) {
            transactionDto.setTransactionStatus(TransactionStatus.PRE_OTP_VERIFICATION);
        } else if(transaction.getStatusId() == TransactionStatus.POST_OTP_VERIFICATION.getId()) {
            transactionDto.setTransactionStatus(TransactionStatus.POST_OTP_VERIFICATION);
        }

        transactionDto.setRetailerRefNumber(transaction.getRetailerRefNumber());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setCreatedOn(transaction.getCreatedOn());
        transactionDto.setUpdatedOn(transaction.getUpdatedOn());
        transactionDto.setAccountId(transaction.getAccountId());
        transactionDto.setTotalAmount(transaction.getTotalAmount());
        transactionDto.setDiscountedAmount(transaction.getDiscountedAmount());

        return transactionDto;
    }

    public static List<TransactionDto> convertToDto(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<TransactionDto>();
        transactions.forEach(transaction -> {
            transactionDtos.add(convertToDto(transaction));
        });

        return transactionDtos;
    }
}
