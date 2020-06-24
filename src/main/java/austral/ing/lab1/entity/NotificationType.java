package austral.ing.lab1.entity;

import austral.ing.lab1.model.Notification;

public class NotificationType {

    private Notification notification;
    private int type;

    public NotificationType(Notification notification, int type) {
        this.notification = notification;
        this.type = type;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationType{" +
                "notification=" + notification.toString() +
                ", type=" + type +
                '}';
    }
}
