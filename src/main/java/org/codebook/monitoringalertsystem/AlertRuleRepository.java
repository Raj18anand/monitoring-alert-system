package org.codebook.monitoringalertsystem;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AlertRuleRepository {
    default List<AlertRule> findByServiceNameAndMetricName(String serviceName, String metricName){
        return new ArrayList<>();
    }
}
