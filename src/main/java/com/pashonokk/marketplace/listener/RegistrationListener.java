package com.pashonokk.marketplace.listener;

import com.pashonokk.marketplace.event.UserRegistrationCompletedEvent;
import com.pashonokk.marketplace.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener {
    private final NotificationService notificationService;

    @EventListener
    @Async
    public void sendNotification(UserRegistrationCompletedEvent event) {
        notificationService.send(event.getMailMessage());
    }
}
