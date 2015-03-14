package org.Marv1n.code.Notifaction.Mail.MailService;

public class MailServiceOptions {

    public String host = "";
    public String port = "";
    public String username = "";
    public String password = "";

    public MailServiceOptions(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public MailServiceOptions(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
