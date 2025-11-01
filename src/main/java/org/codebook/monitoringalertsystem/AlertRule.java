package org.codebook.monitoringalertsystem;

import lombok.Data;

import java.util.List;

@Data
public class AlertRule { // alert rules saved in db, we can find by service name and metric name
    private String metricName;
    private String serviceName;
    private Condition condition;
    private double threshold;
    private Long durationSeconds;
    private List<NotificationChannel> notificationChannels;

    public void addNotificationChannel(NotificationChannel channel) {
        notificationChannels.add(channel);
    }
}


