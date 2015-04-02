package ca.ulaval.glo4002.core.notification;

@FunctionalInterface
public interface NotificationFactory {

    Notification createNotification(NotificationInfo info);
}
