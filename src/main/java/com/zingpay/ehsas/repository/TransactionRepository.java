package com.zingpay.ehsas.repository;

import com.zingpay.ehsas.entity.Transaction;
import org.springframework.stereotype.Repository;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Integer> {
}
