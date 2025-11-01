package org.codebook.monitoringalertsystem;

public class EmailChannel implements NotificationChannel {
    private final String email;

    public EmailChannel(String email) {
        this.email = email;
    }

    public void notify(Alert alert){
        System.out.println("Email sent to " + email);
    }

}
