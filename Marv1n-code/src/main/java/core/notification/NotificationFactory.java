package core.notification;

@FunctionalInterface
public interface NotificationFactory {

    Notification createNotification(NotificationInfo info);
}
