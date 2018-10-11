package utils;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailUtil {
    @Resource(name = "java:jboss/mail/yamail")
    private Session session;

    private static final String SENDER_EMAIL = "mail@gordor.host";
    private static final String ACTIVATION_EMAIL_TEXT = "Для активации аккаунта %s перейдите по ссылке %s";
    private static final String RESET_EMAIL_TEXT = "Для восстановления доступа к аккаунту %s перейдите по ссылке %s";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Активация аккаунта";
    private static final String RESET_EMAIL_SUBJECT = "Восстановление аккаунта";
    private static final String RESET_PASSWORD_HREF = "<a href=\"http://localhost:8081/resetPassword?token=%s\">Восстановить доступ</a>";

    @Asynchronous
    public void send(String email, String subject, String text) {
        try {
            Address address = new InternetAddress(email);
            Address senderAddress = new InternetAddress(SENDER_EMAIL);

            MimeMessage message = new MimeMessage(session);

            message.setFrom("mail@gordor.host");
            message.setSender(senderAddress);
            message.setRecipient(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setContent(text, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("Error on mail send");
        }
    }

    @Asynchronous
    public void sendEmailActivation(String email, String account) {
        String text = String.format(ACTIVATION_EMAIL_TEXT, account, "http://link");
        send(email, ACTIVATION_EMAIL_SUBJECT, text);
    }

    @Asynchronous
    public void sendResetPassword(String email, String account, String token) {
        String resetHref = String.format(RESET_PASSWORD_HREF, token);
        String text = String.format(RESET_EMAIL_TEXT, account, resetHref);
        send(email, RESET_EMAIL_SUBJECT, text);
    }
}
