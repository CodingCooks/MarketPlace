package com.pashonokk.marketplace.controller;

import com.pashonokk.marketplace.dto.UserSavingDto;
import com.pashonokk.marketplace.exception.EntityValidationException;
import com.pashonokk.marketplace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserRegisterController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserRegisterController.class);

    @PostMapping("/users/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserSavingDto userDto, Errors errors) {
        if(errors.hasErrors()){
            errors.getFieldErrors().forEach(er->logger.error(er.getDefaultMessage()));
            throw new EntityValidationException("Validation failed", errors);
        }
        userService.saveRegisteredUser(userDto);
        return ResponseEntity.ok().build();
    }
}
