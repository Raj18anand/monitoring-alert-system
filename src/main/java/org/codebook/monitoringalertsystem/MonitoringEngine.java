package org.codebook.monitoringalertsystem;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MonitoringEngine {

    private final AlertRuleRepository alertRuleRepository;
    
    private final Map<String, Long> violationTracker=new ConcurrentHashMap<>();

    public MonitoringEngine(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    public void processMetric(Metric metric){
        // find the active rules for the service and metric name
        List<AlertRule> rules = alertRuleRepository.findByServiceNameAndMetricName(
                metric.getService(),
                metric.getName()
        );

        for(AlertRule rule:rules){
            evaluateRule(metric,rule);
        }
    }

    private void evaluateRule(Metric metric, AlertRule rule){
        boolean conditionMet = evaluateCondition(metric.getValue(), rule.getCondition(), rule.getThreshold());
        String violationKey = getViolationKey(metric.getService(), metric.getName());

        if(conditionMet){
            handleViolation(metric, rule, violationKey);
        }
        else {
            handleResolution(metric, rule, violationKey);
        }
    }

    private boolean evaluateCondition(double value, Condition condition, double threshold){
        return switch (condition) {
            case    GREATER_THAN -> value > threshold;
            case    LESS_THAN -> value < threshold;
            case    EQUALS -> value == threshold;
            case    GREATER_THAN_OR_EQUAL -> value >= threshold;
            case    LESS_THAN_OR_EQUAL -> value <= threshold;
        };
    }
    
    private String getViolationKey(String serviceName, String metricName){
        return serviceName + ":" + metricName;
    }
    
    private void handleViolation(Metric metric, AlertRule rule, String violationKey){
        Long firstViolationTime = violationTracker.computeIfAbsent(violationKey, k -> metric.getTimestamp());
        long violationDuration = (metric.getTimestamp() - firstViolationTime)/1000;
        
        if(violationDuration > rule.getDurationSeconds()){
            // check if alert already exists, and if not create one and notify to channels
            // Optional<Alert> existingAlert= alertRepository.findByServiceNameAndMetricName(metric.getService(), metric.getName());
            Alert alert = new AlertBuilder()
                    .withServiceName(metric.getService())
                    .withMetricName(metric.getName())
                    .withStatus(AlertStatus.FIRING)
                    .withTriggeredAt(metric.getTimestamp())
                    .withValue(metric.getValue())
                    .withMessage("Alert fired, please resolve the issue")
                    .build();


            // save the alert into alert repository
            
            for(NotificationChannel channel:rule.getNotificationChannels()){
                channel.notify(alert);
            }
        }
    }
    
    private void handleResolution(Metric metric, AlertRule rule, String violationKey){
        violationTracker.remove(violationKey);

        // check if alert exists, and its status as firing, then update it to resolve
        // Optional<Alert> existingAlert= alertRepository.findByServiceNameAndMetricName(metric.getService(), metric.getName());

        Alert existingAlert = new AlertBuilder()
                .withServiceName(metric.getService())
                .withMetricName(metric.getName())
                .withStatus(AlertStatus.FIRING)
                .withTriggeredAt(metric.getTimestamp())
                .withValue(metric.getValue())
                .withMessage("Alert fired, please resolve the issue")
                .build();
        
        if(existingAlert.getStatus() == AlertStatus.FIRING){
            existingAlert.setStatus(AlertStatus.RESOLVED);
            existingAlert.setResolvedAt(metric.getTimestamp());
            existingAlert.setMessage("Issue Resolved on its own, you guys are good for nothing");
            // save the alert into alert repository
            for(NotificationChannel channel:rule.getNotificationChannels()){
                channel.notify(existingAlert);
            }
        }
        
    }

}
