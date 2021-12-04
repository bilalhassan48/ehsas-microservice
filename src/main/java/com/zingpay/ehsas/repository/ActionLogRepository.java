package com.zingpay.ehsas.repository;

import com.zingpay.ehsas.entity.ActionLogs;
import org.springframework.stereotype.Repository;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Repository
public interface ActionLogRepository extends BaseRepository<ActionLogs, Integer> {
}
