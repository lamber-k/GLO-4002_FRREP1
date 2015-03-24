package org.Marv1n.code.Notification;

import org.Marv1n.code.Request;

public interface NotificationFactory {

    Notification createNotification(Request request) throws InvalidRequestException;
}
