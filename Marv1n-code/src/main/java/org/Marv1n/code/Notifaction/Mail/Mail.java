package org.Marv1n.code.Notifaction.Mail;

import java.util.List;

/**
 * Created by Kevin on 08/03/2015.
 */
public class Mail {
    public final String from;
    public final List<String> to;
    public final String object;
    public final String message;

    public Mail(String from, List<String> to, String object, String message) {
        this.from = from;
        this.to = to;
        this.object = object;
        this.message = message;
    }

}
