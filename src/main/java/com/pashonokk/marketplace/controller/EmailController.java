package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/confirmEmail/{token}")
    @ResponseBody
    public String confirmUser(@PathVariable String token) {
        emailService.confirmUserEmail(token);
        return "you are being redirected";
    }

}
