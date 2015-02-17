package org.Marv1n.code.org.Marv1n.code.notifier;

import java.util.List;
import java.util.UUID;

public abstract class Notification {

    protected List<UUID> recieverIDs;

    public List<UUID> getRecieverIDs() {
        return this.recieverIDs;
    }

    public abstract String getNotifactionMessage();
}
