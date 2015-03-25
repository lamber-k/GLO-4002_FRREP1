package org.Marv1n.core.notification;

import org.Marv1n.core.request.Request;

@FunctionalInterface
public interface NotificationFactory {

    Notification createNotification(Request request) throws InvalidRequestException;
}
