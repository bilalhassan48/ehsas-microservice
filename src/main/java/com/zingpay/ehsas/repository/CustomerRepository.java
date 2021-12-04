package com.zingpay.ehsas.repository;

import com.zingpay.ehsas.entity.Customer;
import org.springframework.stereotype.Repository;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Integer> {
}
