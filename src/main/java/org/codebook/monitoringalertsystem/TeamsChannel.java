package org.codebook.monitoringalertsystem;

public class TeamsChannel implements NotificationChannel {
    private final String webhookUrl;

    public TeamsChannel(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void notify(Alert alert){
        System.out.println("Message sent to " + webhookUrl);
    }

}
