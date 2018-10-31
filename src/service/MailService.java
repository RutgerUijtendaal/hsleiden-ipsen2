package service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {

    static Properties mailServerProperties;
    static Session getMailSession;
    private final String gmailUsername;
    private final String password;

    public MailService(String gmailUsername, String password) {
        this.gmailUsername = gmailUsername;
        this.password = password;
    }


    // so this is kinda whacky, but is pretty much the only way without creating
    // two entire new classes just so run() throws a MessagingException
    boolean throwThreadException = false;
    public void threadedSend(String to, String subject, String content) throws MessagingException {

        boolean throwException = false;

        Runnable run = () -> {
            try {
                send(to, subject, content);
            } catch (MessagingException e) {
                throwThreadException = true;
            }
        };

        new Thread(run).start();

        if (throwThreadException) {
            throwThreadException = false;
            throw new MessagingException();
        }

    }

    public void send(String to, String subject, String content) throws MessagingException {
        generateProperties();
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        MimeMessage mimeMessage = generateMessage(to, subject, content);
        //Send Mail
        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", gmailUsername, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    public void generateProperties() {
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
    }

    public MimeMessage generateMessage(String to, String subject, String content) throws MessagingException {
        MimeMessage mailMessage = new MimeMessage(getMailSession);
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mailMessage.setSubject(subject);
        mailMessage.setContent(content, "text/html");
        return mailMessage;
    }

}
