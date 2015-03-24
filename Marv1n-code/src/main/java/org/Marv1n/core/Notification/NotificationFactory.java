package org.Marv1n.core.Notification;

import org.Marv1n.core.Request.Request;

public interface NotificationFactory {

    Notification createNotification(Request request) throws InvalidRequestException;
}
