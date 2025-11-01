package org.codebook.monitoringalertsystem;

import lombok.Data;

@Data
public class Alert {
    private String serviceName;
    private String metricName;
    private AlertStatus status;
    private Long triggeredAt;
    private double value;
    private Long resolvedAt;
    private String message;

    public Alert(AlertBuilder builder) {
        this.serviceName = builder.getServiceName();
        this.metricName = builder.getMetricName();
        this.status = builder.getStatus();
        this.triggeredAt = builder.getTriggeredAt();
        this.value = builder.getValue();
        this.resolvedAt = builder.getResolvedAt();
        this.message = builder.getMessage();
    }
}
