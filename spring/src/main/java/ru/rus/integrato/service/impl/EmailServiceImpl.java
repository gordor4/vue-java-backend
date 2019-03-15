package ru.rus.integrato.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.rus.integrato.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

    private static final String SENDER_EMAIL = "mail@gordor.host";
    private static final String ACTIVATION_EMAIL_TEXT = "Для активации аккаунта %s перейдите по ссылке %s";
    private static final String RESET_EMAIL_TEXT = "Для восстановления доступа к аккаунту %s перейдите по ссылке %s";
    private static final String ACTIVATION_EMAIL_SUBJECT = "Account activation";
    private static final String RESET_EMAIL_SUBJECT = "Reset password";
    private static final String RESET_PASSWORD_HREF = "<a href=\"%s/resetPassword?token=%s\">Восстановить доступ</a>";
    private static final String HOST = "http://site.gordor.host";

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendEmailActivation(String email, String account) {
        String text = String.format(ACTIVATION_EMAIL_TEXT, account, HOST);
        send(email, ACTIVATION_EMAIL_SUBJECT, text);
    }


    public void sendResetPassword(String email, String account, String token) {
        String resetHref = String.format(RESET_PASSWORD_HREF, HOST, token);
        String text = String.format(RESET_EMAIL_TEXT, account, resetHref);
        send(email, RESET_EMAIL_SUBJECT, text);
    }


    private void send(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("mail@gordor.host");
        message.setFrom(SENDER_EMAIL);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}