package utils;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailUtil {
    @Resource(name = "java:jboss/mail/yamail")
    private Session session;

    public void send(String email, String subject, String text) {
        try {
            Address address = new InternetAddress(email);

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("Error on mail send");
        }
    }
}
