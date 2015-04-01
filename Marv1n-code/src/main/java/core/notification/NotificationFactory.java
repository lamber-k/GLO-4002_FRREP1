package core.notification;

import core.request.Request;

@FunctionalInterface
public interface NotificationFactory {

    Notification createNotification(Request request) throws InvalidRequestException;
}
