package org.codebook.monitoringalertsystem;

import lombok.Data;

@Data
public class Metric { // we will receive this from the streaming message
    private String name; // cpu-usage
    private double value; // 95
    private String unit; // percentage, bytes, milliseconds
    private Long timestamp; // millis
    private String service; // order-service
}
