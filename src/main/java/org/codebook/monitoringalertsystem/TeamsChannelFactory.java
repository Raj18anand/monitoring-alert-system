package org.codebook.monitoringalertsystem;

public class TeamsChannelFactory implements NotificationChannelFactory{

    private final String webHookUrl;

    public TeamsChannelFactory(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public NotificationChannel createNotificationChannel() {
        return new TeamsChannel(this.webHookUrl);
    }
}
