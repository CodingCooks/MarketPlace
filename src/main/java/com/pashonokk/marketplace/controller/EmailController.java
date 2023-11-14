package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.EmailDto;
import com.pashonokk.marketplace.service.UserService;
import com.pashonokk.marketplace.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;

    @GetMapping("/confirm-email/{token}")
    public ResponseEntity<Void> confirmUser(@PathVariable String token) {
        emailService.confirmUserEmail(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm/change-password")
    public ResponseEntity<Void> sendLinkToChangePassword(@RequestBody EmailDto emailDto) {
        userService.sendLinkToChangePassword(emailDto.getEmail());
        return ResponseEntity.ok().build();
    }

}
