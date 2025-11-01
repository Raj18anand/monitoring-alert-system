package org.codebook.monitoringalertsystem;

public class EmailChannelFactory implements NotificationChannelFactory{

    private final String email;

    public EmailChannelFactory(String email) {
        this.email = email;
    }

    public NotificationChannel createNotificationChannel() {
        return new EmailChannel(this.email);
    }
}
