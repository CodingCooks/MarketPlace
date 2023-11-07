package com.pashonokk.marketplace.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;

@Data
@AllArgsConstructor
public class UserRegistrationCompletedEvent {
    private SimpleMailMessage mailMessage;
}
