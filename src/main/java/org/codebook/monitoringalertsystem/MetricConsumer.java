package org.codebook.monitoringalertsystem;

public class MetricConsumer {
    private final MonitoringEngine monitoringEngine;

    public MetricConsumer(MonitoringEngine monitoringEngine) {
        this.monitoringEngine=monitoringEngine;
    }

    public void consumeMetric(Metric metric){
        this.monitoringEngine.processMetric(metric);
    }

}
