package ru.rus.integrato.service;

public interface EmailService {
    void sendEmailActivation(String email, String account);
    void sendResetPassword(String email, String account, String token);
}
