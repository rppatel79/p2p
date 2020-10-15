package com.rp.util;

import com.rp.util.application_properties.ApplicationPropertiesFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class Mailer {
    private static final Properties DEFAULT_PROPERTIES = new Properties();

    static {
        DEFAULT_PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
        DEFAULT_PROPERTIES.put("mail.smtp.auth", "true");
        DEFAULT_PROPERTIES.put("mail.debug", "true");
        DEFAULT_PROPERTIES.put("mail.smtp.port", 25);
        DEFAULT_PROPERTIES.put("mail.smtp.socketFactory.port", 25);
        DEFAULT_PROPERTIES.put("mail.smtp.starttls.enable", "true");
        DEFAULT_PROPERTIES.put("mail.transport.protocol", "smtp");
    }

    public static Mailer getDefaultMailer() throws IOException {
        Mailer mailer = new Mailer();
        mailer.setPassword(ApplicationPropertiesFactory.getInstance().getProperty("EMAIL_PASSWORD"));
        mailer.setUsername(ApplicationPropertiesFactory.getInstance().getProperty("EMAIL_USERNAME"));

        Properties properties = new Properties();
        properties.putAll(ApplicationPropertiesFactory.getInstance().getProperties("mail.*"));
        mailer.setSessionProperties(properties);

        return mailer;
    }

    private Properties properties_;
    private String username_;
    private String password_;

    public void setSessionProperties(Properties properties)
    {
        properties_=properties;
    }

    public void setUsername(String username) {
        username_ = username;
    }

    public void setPassword(String password) {
        password_ = password;
    }

    public static void main(String[] args) throws Exception {
        Mailer mailer = Mailer.getDefaultMailer();

        mailer.sendMessage(Collections.singleton(ApplicationPropertiesFactory.getInstance().getProperty("EMAIL_TO")), "MySubject", "MyBody");
    }

    public void sendMessage(Collection<String> toAddress, String subject, String body) throws MessagingException {

        Session mailSession;

        mailSession = Session.getInstance(properties_,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username_, password_);
                    }
                });

        Transport transport = mailSession.getTransport();

        MimeMessage  message = new MimeMessage(mailSession);

        message.setSubject(subject);
        message.setFrom(new InternetAddress(username_));
        String []to = new String[toAddress.size()];
        toAddress.toArray(to);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
        message.setContent(body,"text/html");
        transport.connect();

        transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
