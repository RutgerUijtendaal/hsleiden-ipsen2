package service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Service to send mail
 *
 * @author Stefan de Keijzer
 * @author Jordi Dorren
 */
public class MailService {

    private static Properties mailServerProperties;
    private static Session getMailSession;
    private final String gmailUsername;
    private final String password;

    private MessagingException exception = null;

    public MailService(String gmailUsername, String password) {
        this.gmailUsername = gmailUsername;
        this.password = password;
    }


    /**
     * Make a new thread and sends an email in the corresponding thread
     * @param to email address the mail has to be send to
     * @param subject subject of the mail
     * @param content content of the mail
     * @throws MessagingException
     */
    public void threadedSend(String to, String subject, String content) throws MessagingException {

        Runnable run = () -> {
            try {
                send(to, subject, content);
            } catch (MessagingException e) {
                MailService.this.exception = e;
            }
        };

        new Thread(run).start();

        if (this.exception != null) {
            MessagingException tmpException = this.exception;
            this.exception = null;
            throw tmpException;
        }

    }

    /**
     * Send an email
     * @param to email address
     * @param subject subject of the email
     * @param content content of the email
     * @throws MessagingException thrown when something is wrong
     */
    private void send(String to, String subject, String content) throws MessagingException {
        generateProperties();
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        MimeMessage mimeMessage = generateMessage(to, subject, content);
        //Send Mail
        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", gmailUsername, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    /**
     * Generates the properties of the mailing server
     */
    private void generateProperties() {
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
    }

    /**
     * Generates a mail
     * @param to email address
     * @param subject subject of the email
     * @param content content of the email
     * @return
     * @throws MessagingException
     */
    private MimeMessage generateMessage(String to, String subject, String content) throws MessagingException {
        MimeMessage mailMessage = new MimeMessage(getMailSession);
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mailMessage.setSubject(subject);
        mailMessage.setContent(content, "text/html");
        return mailMessage;
    }

}
