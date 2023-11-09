package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.service.UserService;
import com.pashonokk.marketplace.service.impl.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;
    private final UserService userService;

    @GetMapping("/confirm-email/{token}")
    @ResponseBody
    public String confirmUser(@PathVariable String token) {
        emailService.confirmUserEmail(token);
        return "you are being redirected to login page";
    }

    @GetMapping("/confirm/change-password")
    @ResponseBody
    public String sendLinkToChangePassword(@RequestBody String email, HttpSession session) {
        userService.sendLinkToChangePassword(email);
        session.setAttribute("email", email);
        return "We have sent you an email with link to change password";
    }

}
