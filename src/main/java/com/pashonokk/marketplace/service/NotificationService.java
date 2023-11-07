package com.pashonokk.marketplace.service;

import org.springframework.mail.SimpleMailMessage;

public interface NotificationService {
    void send(SimpleMailMessage mailMessage);
}
