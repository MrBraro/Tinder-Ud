package com.tinder.ud.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/*
 * Clase creada para el envio de correo con confirmación al email de la persona
 * registrada
 * 
 * @author Juan Sebastian BRavo Rojas
 * @version 1.0
 * @since 2025-12-09
*/
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendWelcomeEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Tinder UD");
        message.setText("Welcome! Your account has been successfully created.");
        javaMailSender.send(message);
    }
}
