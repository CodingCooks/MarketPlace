package com.pashonokk.marketplace.service.impl;

import com.pashonokk.marketplace.entity.User;
import com.pashonokk.marketplace.service.NotificationService;
import com.pashonokk.marketplace.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService implements NotificationService {

    private final JavaMailSender emailSender;
    private final TokenService tokenService;

    @SneakyThrows
    @Override
    public void send(SimpleMailMessage mailMessage) {
        emailSender.send(mailMessage);
    }

    @Transactional
    public void confirmUserEmail(String token) {
        User userByTokenValue = tokenService.validateToken(token);
        userByTokenValue.setIsVerified(true);
    }

}
