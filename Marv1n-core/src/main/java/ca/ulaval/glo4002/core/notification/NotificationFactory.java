package ca.ulaval.glo4002.core.notification;

import ca.ulaval.glo4002.core.request.Request;

@FunctionalInterface
public interface NotificationFactory {

    Notification createNotification(Request request);
}
