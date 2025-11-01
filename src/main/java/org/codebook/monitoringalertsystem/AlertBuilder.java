package org.codebook.monitoringalertsystem;

import lombok.Data;

@Data
public class AlertBuilder{
    private String serviceName;
    private String metricName;
    private AlertStatus status;
    private Long triggeredAt;
    private double value;
    private Long resolvedAt;
    private String message;

    public AlertBuilder withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public AlertBuilder withMetricName(String metricName) {
        this.metricName = metricName;
        return this;
    }

    public AlertBuilder withStatus(AlertStatus status) {
        this.status = status;
        return this;
    }

    public AlertBuilder withTriggeredAt(Long triggeredAt) {
        this.triggeredAt = triggeredAt;
        return this;
    }

    public AlertBuilder withValue(double value) {
        this.value = value;
        return this;
    }

    public AlertBuilder withResolvedAt(Long resolvedAt) {
        this.resolvedAt = resolvedAt;
        return this;
    }

    public AlertBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public Alert build() {
        return new Alert(this);
    }
}
